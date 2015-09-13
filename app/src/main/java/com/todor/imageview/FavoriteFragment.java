package com.todor.imageview;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private GridView imageGridView;
    private GridViewAdapter myAdapter;
    public static ArrayList<ImageItem> imageItemArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        imageGridView = (GridView) view.findViewById(R.id.galleryView);
//        imageItemArrayList = GalleryImages.getGalleryImages(getActivity());
//        ArrayList<ImageItem> myArray = GalleryImages.getGalleryImages(getActivity());
//        for(ImageItem item : GalleryImages.selectedArray) {
//            if (item.isSelected())
//                imageItemArrayList.add(item);
//        }
        myAdapter = new GridViewAdapter(getActivity(), imageItemArrayList);
        imageGridView.setAdapter(myAdapter);

        return view;
    }
}
