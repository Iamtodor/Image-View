package com.todor.imageview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.GridViewAdapter;
import com.todor.imageview.R;
import com.todor.imageview.model.ImageItem;

public class FolderFragment extends Fragment implements View.OnClickListener {

    private GridView imageGridView;
    private GridViewAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);
        imageGridView = (GridView) view.findViewById(R.id.galleryView);
        myAdapter = new GridViewAdapter(getActivity(), GalleryImages.getGalleryImages(getActivity()), this);
        imageGridView.setAdapter(myAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("In folder");

        return view;
    }

    @Override
    public void onClick(View view) {
        GalleryImages.saveOrDeleteFavorite((ImageItem) myAdapter.getItem(imageGridView.getPositionForView(view)));
        myAdapter.notifyDataSetChanged();
    }
}
