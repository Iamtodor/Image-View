package com.todor.imageview.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.R;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView);
        String path = getIntent().getStringExtra("path");
        String[] pathArray = path.split("/");
        getSupportActionBar().setTitle(pathArray[pathArray.length - 1]);
        Log.d("path after intent: ", path);

        Picasso.with(this)
                .load("file://" + Uri.parse(path))
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("Tapp on back", "true");
                this.onBackPressed();
                return (true);
            // more code here for other cases
        }
        return false;
    }

}
