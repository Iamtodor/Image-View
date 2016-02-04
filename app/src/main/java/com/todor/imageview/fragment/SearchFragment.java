package com.todor.imageview.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.todor.imageview.MyGridLayoutManager;
import com.todor.imageview.R;
import com.todor.imageview.RecyclerViewAdapterForSearch;
import com.todor.imageview.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class SearchFragment extends Fragment {
    public final int MAX_RESULT_COUNT = 50;
    private final int PAGE_SIZE = 8;
    private final String SEARCH_ENDPOINT = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + PAGE_SIZE;
    private Toolbar searchToolbar;
    private Toolbar toolbar;
    private RecyclerViewAdapterForSearch recyclerViewAdapter;
    private MyGridLayoutManager recyclerView;
    private String searchRequest;
    private EditText searchInput;

    @Override
    public void onPause() {
        super.onPause();
        if (searchToolbar != null && toolbar != null) {
            toolbar.removeView(searchToolbar);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchRequest", searchRequest);
        outState.putSerializable("adapter", recyclerViewAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            searchRequest = savedInstanceState.getString("searchRequest");
            searchInput.setText(searchRequest);
            recyclerViewAdapter = (RecyclerViewAdapterForSearch) savedInstanceState.getSerializable("adapter");
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewForRecycler = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (MyGridLayoutManager) viewForRecycler.findViewById(R.id.recyclerView);
//        searchToolbar = (Toolbar) viewForRecycler.findViewById(R.id.toolbar);
//        ImageButton searchButton = (ImageButton) searchToolbar.findViewById(R.id.search_button);
//        searchInput = (EditText) searchToolbar.findViewById(R.id.search_input);
//        ((MainActivity)getActivity()).setSupportActionBar(searchToolbar);

//        if (!isNetworkAvailable()) {
//            Toast.makeText(getActivity(), "You have no internet", Toast.LENGTH_SHORT).show();
//            searchToolbar.setVisibility(View.INVISIBLE);
//        }

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerViewAdapter = new RecyclerViewAdapterForSearch(getActivity());
//                recyclerView.setAdapter(recyclerViewAdapter);
//                recyclerView.invalidate();
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                searchRequest = searchInput.getText().toString();
//                new SearchTask(SearchFragment.this).execute(searchInput.getText().toString());
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
//            }
//        });
        return viewForRecycler;
    }

    protected void asyncJson(String url, final int start, final RecyclerViewAdapterForSearch adapter) {

        final AQuery aq = new AQuery(getView().findViewById(R.id.recyclerView));
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                if (!isAttached(adapter)) return;

                if (json != null) {
                    try {
                        JSONObject responseData = json.getJSONObject("responseData");
                        final JSONArray results = responseData.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            final ImageItem imageItem = new ImageItem();
                            imageItem.setPath(results.getJSONObject(i).getString("tbUrl"));
                            imageItem.setName(URLUtil.guessFileName(url, null, null));
                            imageItem.setDate(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").
                                    format(System
                                            .currentTimeMillis())));
                            imageItem.setResultIndex(start + i);
                            imageItem.setFavorite(false);

                            if (imageItem.getResultIndex() >= MAX_RESULT_COUNT) {
                                continue;
                            }

                            aq.ajax(imageItem.getPath(), Bitmap.class, new AjaxCallback<Bitmap>() {
                                @Override
                                public void callback(String url, Bitmap bitmap, AjaxStatus status) {
                                    if (!isAttached(adapter)) return;
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

    protected boolean isAttached(RecyclerViewAdapterForSearch adapter) {
        return recyclerView.getAdapter() == adapter;
    }

    protected void runSearch(String query) {
        for (int i = 0; i < MAX_RESULT_COUNT; i += PAGE_SIZE) {
            try {
                asyncJson(SEARCH_ENDPOINT + "&q=" + URLEncoder.encode(query, "UTF-8") + "&start=" + i, i, recyclerViewAdapter);
            } catch (UnsupportedEncodingException e) {
                // TODO: handle error
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
