package com.todor.imageview.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.R;
import com.todor.imageview.activity.AdapterListener;
import com.todor.imageview.activity.ImageActivity;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.model.ImageItem;

import java.io.File;

public class FavoriteSlideFragment extends Fragment {

    public static final String IMAGE_KEY = "image";
    private AdapterListener adapterListener;

    private ImageItem imageItem;

    public static FavoriteSlideFragment getInstance(ImageItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGE_KEY, item);
        FavoriteSlideFragment fragment = new FavoriteSlideFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageItem = (ImageItem) getArguments().getSerializable(IMAGE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite_page_layout, null);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(imageItem.getName());

        final ImageView imageView = (ImageView) v.findViewById(R.id.image_thumb);
        TextView name = (TextView) v.findViewById(R.id.setName);
        TextView size = (TextView) v.findViewById(R.id.setSize);
        TextView width = (TextView) v.findViewById(R.id.setWidth);
        TextView height = (TextView) v.findViewById(R.id.setHeight);
        TextView date = (TextView) v.findViewById(R.id.setDate);
        TextView path = (TextView) v.findViewById(R.id.setUrl);
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryImages.deleteFavorite(imageItem);
                adapterListener.removeImageItem(imageItem);
            }
        });

            Picasso.with(getActivity())
                    .load("file://" + Uri.parse(imageItem.getPath()))
                    .into(imageView);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //Returns null, sizes are in the options variable
        BitmapFactory.decodeFile(imageItem.getPath(), options);
        int widthInt = options.outWidth;
        int heightInt = options.outHeight;
        File file = new File(imageItem.getPath());
        long sizeLong = file.length() / 1024;

        name.setText(imageItem.getName());
        size.setText(String.valueOf(sizeLong));
        width.setText(String.valueOf(widthInt));
        height.setText(String.valueOf(heightInt));
        date.setText(String.valueOf(imageItem.getDate()));
        path.setText(imageItem.getPath());
        adapterListener = (AdapterListener) getParentFragment();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), ImageActivity.class);
                mIntent.putExtra("path", imageItem.getPath());
                startActivity(mIntent);
            }
        });

        return v;
    }
}
