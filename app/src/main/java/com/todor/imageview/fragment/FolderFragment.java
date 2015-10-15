package com.todor.imageview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.todor.imageview.CustomDialog;
import com.todor.imageview.DialogListener;
import com.todor.imageview.MyGridLayoutManager;
import com.todor.imageview.R;
import com.todor.imageview.RecyclerViewAdapterForFolder;
import com.todor.imageview.activity.MainActivity;
import com.todor.imageview.model.GalleryImages;

import java.io.File;

public class FolderFragment extends Fragment implements DialogListener {

    private RecyclerViewAdapterForFolder mAdapter;
    private MyGridLayoutManager recyclerView;
    private Toolbar toolbar;
    private View view;
    private TextView pathView;
    private ImageView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_folder, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        pathView = (TextView) toolbar.findViewById(R.id.path);
        search = (ImageView) toolbar.findViewById(R.id.folder_button);
//        toolbar.addView(view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog();
                dialog.setTargetFragment(FolderFragment.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "show");
            }
        });

        pathView.setText("");

        View view = inflater.inflate(R.layout.recycle_view, container, false);
        recyclerView = (MyGridLayoutManager) view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapterForFolder(GalleryImages.getImageFromFolder("", getActivity()),
                getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (view != null && toolbar != null) {
//            toolbar.removeView(view);
//        }
//    }

    @Override
    public void getFile(File file) {
        pathView.setText(file.getPath());
        mAdapter = new RecyclerViewAdapterForFolder(GalleryImages.getImageFromFolder(file.getPath(), getActivity()),
                getActivity());
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

}
