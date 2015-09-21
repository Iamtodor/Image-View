package com.todor.imageview;

import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.todor.imageview.fragment.SearchFragment;
import com.todor.imageview.model.ImageResultForSearch;

public class ImageAdapterForSearch extends BaseAdapter {

    private SearchFragment fragment;
    private int contiguousResults; //Contiguous results count form front of results array
    private SimpleArrayMap<Integer, ImageResultForSearch> results;

    public ImageAdapterForSearch(SearchFragment fragment) {
        this.contiguousResults = 0;
        this.results = new SimpleArrayMap<>();
        this.fragment = fragment;
    }

    public int getCount() {
        return Math.min(contiguousResults, 100);
    }

    public Object getItem(int position) {
        return results.valueAt(position);
    }

    public long getItemId(int position) {
        //not implemented, not needed for this application
        return 0;
    }

    public void addResult(ImageResultForSearch result) {
        this.results.put(result.resultIndex, result);
        int i = contiguousResults;
        while (results.get(i) != null) {
            i++;
        }

        if (i != contiguousResults) {
            contiguousResults = i;
            notifyDataSetChanged();
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(fragment.getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, 300));
        } else {
            imageView = (ImageView) convertView;
            imageView.setImageResource(android.R.color.transparent);
        }

        String imageUrl = results.get(position).imgUrl;
        imageView.setImageBitmap(fragment.imageCache.get(imageUrl));

        return imageView;
    }
}