package com.todor.imageview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.imageview.MyGridLayoutManager;
import com.todor.imageview.R;
import com.todor.imageview.RecyclerViewAdapterForFolder;
import com.todor.imageview.model.GalleryImages;

public class FolderFragment extends Fragment {

    private RecyclerViewAdapterForFolder mAdapter;
    private MyGridLayoutManager recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("In folder");

        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (MyGridLayoutManager) view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapterForFolder(GalleryImages.getGalleryImages(getActivity()),
                getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
