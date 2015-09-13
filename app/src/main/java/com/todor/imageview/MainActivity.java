package com.todor.imageview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.todor.imageview.utils.Utils;


public class MainActivity extends AppCompatActivity {

    private Drawer.Result myDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.frame_container, new FolderFragment())
            .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDrawer = Utils.createCommonDrawer(MainActivity.this, toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FolderFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if(myDrawer.isDrawerOpen())
            myDrawer.closeDrawer();
        else
            super.onBackPressed();
    }


}
