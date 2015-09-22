package com.todor.imageview.utils;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.todor.imageview.fragment.FavoriteFragment;
import com.todor.imageview.fragment.FolderFragment;
import com.todor.imageview.R;
import com.todor.imageview.fragment.SearchFragment;
//import com.todor.imageview.fragment.SearchFragment;

public class Utils {
    public static void hideSoftKeyBoard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d("Utils.hideSoftKeyBoard: ", String.valueOf(e));
        }
    }

    public static Drawer.Result createCommonDrawer(final AppCompatActivity activity, Toolbar toolbar) {
        Drawer.Result drawerResult;
        drawerResult = new Drawer()
                .withActivity(activity)
                .withDrawerWidthDp(250)
                .withHeader(R.layout.drawer_header)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Folder").withIcon(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.folder)).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Search").withIcon(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.search)).withIdentifier(2),
                        new PrimaryDrawerItem().withName("My favorites").withIcon(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.starnoselected)).withIdentifier(3)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View view) {
                        hideSoftKeyBoard(activity);
                    }

                    @Override
                    public void onDrawerClosed(View view) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if(iDrawerItem != null) {
                            if(iDrawerItem.getIdentifier() == 1) {
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FolderFragment()).commit();
                            } else if(iDrawerItem.getIdentifier() == 2) {
                                activity.getSupportFragmentManager().beginTransaction().addToBackStack("search").replace(R.id.frame_container, new SearchFragment()).commit();
                            } else if(iDrawerItem.getIdentifier() == 3) {
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FavoriteFragment()).commit();
                            }

                        }
                    }
                })
                .build();

        return drawerResult;
    }
}
