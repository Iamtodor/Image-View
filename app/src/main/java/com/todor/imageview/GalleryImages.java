package com.todor.imageview;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class GalleryImages {
    public static ArrayList<ImageItem> selectedArray;

    @Nullable
    public static ArrayList<ImageItem> getGalleryImages(Activity activity) {
        ArrayList<ImageItem> imageItems = null;
        ImageItem imageItem;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor externalImageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        if(externalImageCursor.moveToFirst()) {
            imageItems = new ArrayList<>(externalImageCursor.getCount());
            int index = externalImageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                imageItem = new ImageItem();
                imageItem.setImage(externalImageCursor.getString(index));
                imageItems.add(imageItem);
            } while (externalImageCursor.moveToNext());
        }
        return imageItems;
    }

}
