package com.todor.imageview.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.todor.imageview.ImageAdapterForSearch;
import com.todor.imageview.R;
import com.todor.imageview.model.ImageResultForSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchFragment extends Fragment {
    public final int MAX_RESULT_COUNT = 50;
    private final int PAGE_SIZE = 8;
    private final String SEARCH_ENDPOINT = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + PAGE_SIZE;
    public LruCache<String, Bitmap> imageCache;
    View searchToolbar;
    Toolbar toolbar;
    private ImageAdapterForSearch resultsImageAdapter;
    private GridView resultsGrid;

    @Override
    public void onPause() {
        super.onPause();
        if (searchToolbar != null && toolbar != null) {
            toolbar.removeView(searchToolbar);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        resultsGrid = (GridView) v.findViewById(R.id.grid_view);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        searchToolbar = inflater.inflate(R.layout.toolbar_search, toolbar, false);
        ImageButton searchButton = (ImageButton) searchToolbar.findViewById(R.id.search_button);
        final EditText searchInput = (EditText) searchToolbar.findViewById(R.id.search_input);
        toolbar.addView(searchToolbar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsImageAdapter = new ImageAdapterForSearch(SearchFragment.this);
                resultsGrid.setAdapter(resultsImageAdapter);
                resultsGrid.invalidateViews();
                new SearchTask(SearchFragment.this).execute(searchInput.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
            }
        });
        return v;
    }

    protected void asyncJson(String url, final int start, final ImageAdapterForSearch searchResultsAdapter) {

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        imageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        final AQuery aq = new AQuery(getView().findViewById(R.id.mainLayoutRoot));
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                if (!isAttached(searchResultsAdapter)) return;

                if (json != null) {
                    try {
                        JSONObject responseData = json.getJSONObject("responseData");
                        JSONArray results = responseData.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            final ImageResultForSearch imageResult = new ImageResultForSearch();
                            imageResult.imgUrl = results.getJSONObject(i).getString("tbUrl");
                            imageResult.resultIndex = start + i;

                            if (imageResult.resultIndex >= MAX_RESULT_COUNT) {
                                continue;
                            }

                            aq.ajax(imageResult.imgUrl, Bitmap.class, new AjaxCallback<Bitmap>() {
                                @Override
                                public void callback(String url, Bitmap bitmap, AjaxStatus status) {

                                    if (!isAttached(searchResultsAdapter)) return;

                                    imageCache.put(url, bitmap);
                                    resultsImageAdapter.addResult(imageResult);
                                    resultsGrid.invalidateViews();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //TODO: handle errors
                }
            }
        });
    }

    protected boolean isAttached(ImageAdapterForSearch adapter) {
        return resultsGrid.getAdapter() == adapter;
    }

    protected void runSearch(String query) {

        for (int i = 0; i < MAX_RESULT_COUNT; i += PAGE_SIZE) {
            try {
                asyncJson(SEARCH_ENDPOINT + "&q=" + URLEncoder.encode(query, "UTF-8") + "&start=" + i, i, resultsImageAdapter);
            } catch (UnsupportedEncodingException e) {
                // TODO: handle error
            }
        }
    }
}

class SearchTask extends AsyncTask<String, Void, Void> {

    private SearchFragment fragment;

    public SearchTask(SearchFragment fragment) {
        this.fragment = fragment;
    }

    protected Void doInBackground(String... args) {
        fragment.runSearch(args[0]);
        return null;
    }

}
