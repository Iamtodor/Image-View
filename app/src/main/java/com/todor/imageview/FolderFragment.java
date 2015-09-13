package com.todor.imageview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class FolderFragment extends Fragment {

    private GridView imageGridView;
    private GridViewAdapter myAdapter;
    private static ArrayList<ImageItem> imageItemArrayList;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Images", imageItemArrayList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null)
            imageItemArrayList = (ArrayList<ImageItem>) savedInstanceState.getSerializable("Images");
        else
            imageItemArrayList = GalleryImages.getGalleryImages(getActivity());

        View view = inflater.inflate(R.layout.fragment_folder, container, false);
        imageGridView = (GridView) view.findViewById(R.id.galleryView);
        myAdapter = new GridViewAdapter(getActivity(), imageItemArrayList);
        imageGridView.setAdapter(myAdapter);

        return view;
    }

}
