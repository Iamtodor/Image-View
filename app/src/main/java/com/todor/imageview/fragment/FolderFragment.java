package com.todor.imageview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;

import com.todor.imageview.GetItemFromRecyclerViewListener;
import com.todor.imageview.MyGridLayoutManager;
import com.todor.imageview.RecyclerViewAdapter;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.GridViewAdapter;
import com.todor.imageview.R;
import com.todor.imageview.model.ImageItem;

public class FolderFragment extends Fragment implements View.OnClickListener, GetItemFromRecyclerViewListener {

    private GridView imageGridView;
    private GridViewAdapter myAdapter;
    private RecyclerViewAdapter mAdapter;
    private MyGridLayoutManager recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_folder, container, false);
//        imageGridView = (GridView) view.findViewById(R.id.galleryView);
//        myAdapter = new GridViewAdapter(getActivity(), GalleryImages.getGalleryImages(getActivity()), this);
//        imageGridView.setAdapter(myAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("In folder");

        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (MyGridLayoutManager) view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(GalleryImages.getGalleryImages(getActivity()),
                        getActivity(), this, this);
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 6, 0, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
//        GalleryImages.saveOrDeleteFavorite((ImageItem) myAdapter.getItem(imageGridView.getPositionForView(view)));
    }


    @Override
    public void getImageItem(View v, int position) {
//        GalleryImages.saveOrDeleteFavorite();
        myAdapter.notifyDataSetChanged();
    }
}
