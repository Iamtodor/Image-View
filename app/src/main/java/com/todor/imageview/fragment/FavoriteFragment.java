package com.todor.imageview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.todor.imageview.R;
import com.todor.imageview.activity.AdapterListener;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.model.ImageItem;

import java.util.List;

public class FavoriteFragment extends Fragment implements AdapterListener {

    private List<ImageItem> imageItems;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite_view_pager, container, false);
        imageItems = GalleryImages.getFavorites();
        if(imageItems.size() != 0) {
            mPager = (ViewPager) v.findViewById(R.id.pager);
            mPagerAdapter = new PagerAdapter(getChildFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(0);
        } else {
            Toast.makeText(getContext(), "You have no favorite image", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void removeImageItem(ImageItem imageItem) {
        GalleryImages.saveOrDeleteFavorite(imageItem);
        imageItems = GalleryImages.getFavorites();
        mPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return FavoriteSlideFragment.getInstance(imageItems.get(imageItems.size() - 1 - position));
        }

        @Override
        public int getCount() {
            return imageItems.size();
        }

    }

}
