package com.todor.imageview.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.todor.imageview.R;
import com.todor.imageview.RecyclerViewAdapterForSearch;
import com.todor.imageview.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchFragment extends Fragment {
    public final int MAX_RESULT_COUNT = 50;
    private final int PAGE_SIZE = 8;
    private final String SEARCH_ENDPOINT = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + PAGE_SIZE;
    View searchToolbar;
    Toolbar toolbar;
    private RecyclerViewAdapterForSearch recyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onPause() {
        super.onPause();
        if (searchToolbar != null && toolbar != null) {
            toolbar.removeView(searchToolbar);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewForRecycler = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (RecyclerView) viewForRecycler.findViewById(R.id.recyclerView);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        searchToolbar = inflater.inflate(R.layout.toolbar_search, toolbar, false);
        ImageButton searchButton = (ImageButton) searchToolbar.findViewById(R.id.search_button);
        final EditText searchInput = (EditText) searchToolbar.findViewById(R.id.search_input);
        toolbar.addView(searchToolbar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter = new RecyclerViewAdapterForSearch(SearchFragment.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.invalidate();
                new SearchTask(SearchFragment.this).execute(searchInput.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
            }
        });
        return viewForRecycler;
    }

    protected void asyncJson(String url, final int start, final RecyclerViewAdapterForSearch adapter) {

        final AQuery aq = new AQuery(getView().findViewById(R.id.recyclerView));
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

//                if (!isAttached(adapter)) return;

                if (json != null) {
                    try {
                        JSONObject responseData = json.getJSONObject("responseData");
                        final JSONArray results = responseData.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {

                            final ImageItem imageItem = new ImageItem();

                            imageItem.setPath(results.getJSONObject(i).getString("tbUrl"));
                            imageItem.setResultIndex(start + i);
                            imageItem.setFavorite(false);

                            if (imageItem.getResultIndex() >= MAX_RESULT_COUNT) {
                                continue;
                            }

                            aq.ajax(imageItem.getPath(), Bitmap.class, new AjaxCallback<Bitmap>() {
                                @Override
                                public void callback(String url, Bitmap bitmap, AjaxStatus status) {
//                                    if (!isAttached(adapter)) return;
                                    recyclerViewAdapter.addResult(imageItem);
                                    recyclerView.invalidate();
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

//    protected boolean isAttached(ImageAdapterForSearch adapter) {
//        return resultsGrid.getAdapter() == adapter;
//    }

    protected void runSearch(String query) {
        for (int i = 0; i < MAX_RESULT_COUNT; i += PAGE_SIZE) {
            try {
                asyncJson(SEARCH_ENDPOINT + "&q=" + URLEncoder.encode(query, "UTF-8") + "&start=" + i, i, recyclerViewAdapter);
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
