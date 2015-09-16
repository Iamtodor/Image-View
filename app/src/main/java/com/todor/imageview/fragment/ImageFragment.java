package com.todor.imageview.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.todor.imageview.R;

public class ImageFragment  extends Fragment{

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView) v.findViewById(R.id.image);
        getActivity().getActionBar().setHomeButtonEnabled(true);

        Toolbar toolbar = (Toolbar) getActivity().getActionBar().getCustomView();
        toolbar.setTitle("Some title");

        return v;
    }
}
