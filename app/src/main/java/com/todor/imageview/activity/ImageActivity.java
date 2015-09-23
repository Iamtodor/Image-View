package com.todor.imageview.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_screen);

        imageView = (ImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        byte[] bitmapArray = getIntent().getByteArrayExtra("stream");
        if (bitmapArray != null) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            if (mBitmap != null)
                imageView.setImageBitmap(mBitmap);
        } else {
            String path = getIntent().getStringExtra("path");
            String[] pathArray = path.split("/");
            getSupportActionBar().setTitle(pathArray[pathArray.length - 1]);

            Picasso.with(this)
                    .load("file://" + Uri.parse(path))
                    .into(imageView);
        }
        mAttacher = new PhotoViewAttacher(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return (true);
        }
        return false;
    }

}
