package com.todor.imageview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.todor.imageview.R;
import com.todor.imageview.fragment.FolderFragment;
import com.todor.imageview.utils.Utils;


public class MainActivity extends AppCompatActivity {

    private Drawer.Result myDrawer;
    private Fragment fragment;
    private Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, new FolderFragment(), "folder")
                    .commit();
        } else {
//            fragment = getSupportFragmentManager().getFragment(
//                    savedInstanceState, "search");
        }
//        setSupportActionBar(toolbar);
        myDrawer = Utils.createCommonDrawer(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (myDrawer.isDrawerOpen())
            myDrawer.closeDrawer();
        else {
            if (doubleBackToExitPressedOnce)
                super.onBackPressed();
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

//    public Toolbar getToolbar() {
//        return toolbar;
//    }
}
