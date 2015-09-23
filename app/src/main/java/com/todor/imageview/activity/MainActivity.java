package com.todor.imageview.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.todor.imageview.R;
import com.todor.imageview.fragment.FolderFragment;
import com.todor.imageview.utils.Utils;


public class MainActivity extends AppCompatActivity {

    private Drawer.Result myDrawer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, new FolderFragment(), "folder")
                    .commit();
        } else {
            fragment = getSupportFragmentManager().getFragment(
                    savedInstanceState, "search");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDrawer = Utils.createCommonDrawer(MainActivity.this, toolbar);
    }

    @Override
    public void onBackPressed() {
        if (myDrawer.isDrawerOpen())
            myDrawer.closeDrawer();
        else
            super.onBackPressed();
    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        FragmentManager.BackStackEntry backEntry = (FragmentManager.BackStackEntry) getFragmentManager().getBackStackEntryAt(MainActivity.this.getFragmentManager().getBackStackEntryCount());
//        String str = backEntry.getName();
//        fragment = getSupportFragmentManager().findFragmentByTag(str);
//
//        getSupportFragmentManager().putFragment(outState, "search", fragment);
//
//    }
}
