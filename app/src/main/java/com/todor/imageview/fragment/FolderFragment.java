package com.todor.imageview.fragment;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.todor.imageview.CustomDialog;
import com.todor.imageview.MyGridLayoutManager;
import com.todor.imageview.R;
import com.todor.imageview.RecyclerViewAdapter;
import com.todor.imageview.model.GalleryImages;

public class FolderFragment extends Fragment {

    private RecyclerViewAdapter mAdapter;
    private MyGridLayoutManager recyclerView;
    private Toolbar toolbar;
    private View toolbarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("In folder");
        toolbarView = inflater.inflate(R.layout.toolbar_folder, container, false);
        TextView pathView = (TextView) toolbarView.findViewById(R.id.path);
        ImageButton search = (ImageButton) toolbarView.findViewById(R.id.folder_button);
        toolbar.addView(toolbarView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog();
                dialog.show(getActivity().getFragmentManager(), "show");
            }
        });

        pathView.setText("KatePhotos");

        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (MyGridLayoutManager) view.findViewById(R.id.recyclerView);
//        mAdapter = new RecyclerViewForFolder(GalleryImages.getGalleryImages(getActivity()),
//                getActivity());
        mAdapter = new RecyclerViewAdapter(GalleryImages.getImageFromFolder("KatePhotos"),
                getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (toolbarView != null && toolbar != null) {
            toolbar.removeView(toolbarView);
        }
    }
}
