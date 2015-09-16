package com.todor.imageview.model;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.todor.imageview.utils.DataBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GalleryImages {
    static DataBase dataBase;

    @Nullable
    public static ArrayList<ImageItem> getGalleryImages(Activity activity) {
        dataBase = new DataBase(activity);
        ArrayList<ImageItem> imageItems = null;
        ArrayList<ImageItem> favoriteImageItems;
        ImageItem imageItem;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity
                .getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        if(cursor.moveToFirst()) {
            imageItems = new ArrayList<>(cursor.getCount());
            favoriteImageItems = dataBase.getFavorites();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                imageItem = new ImageItem();
                imageItem.setPath(cursor.getString(index));
                for(ImageItem favoriteImage : favoriteImageItems) {
                    if(favoriteImage.getPath().equals(imageItem.getPath()))
                        imageItem.setFavorite(true);
                }
                Uri filePathUri = Uri.parse(cursor.getString(index));
                String fileName = filePathUri.toString();
                String[] path = fileName.split("/");
                imageItem.setName(path[path.length - 1]);
                imageItem.setPath(filePathUri.toString());
                long date = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                imageItem.setDate(String.valueOf(simpleDateFormat.format(date)));
                imageItems.add(imageItem);
            } while (cursor.moveToNext());
        }

//        ArrayList<String> f = new ArrayList<>();
//        File[] listFile;
//
//        File file= new File(android.os.Environment.getExternalStorageDirectory(),"Download");
//        if (file.isDirectory()) {
//            listFile = file.listFiles();
//            for (int i = 0; i < listFile.length; i++) {
//                f.add(listFile[i].getAbsolutePath());
//            }
//        }

        return imageItems;
    }

    public static void saveOrDeleteFavorite(ImageItem image) {
        if(image.isFavorite()) {
            image.setFavorite(false);
            dataBase.deleteFavorite(image);
        } else {
            image.setFavorite(true);
            dataBase.saveFavorite(image);
        }
    }

    public static void deleteFavorite(ImageItem imageItem) {

    }

    public static void saveFavorites(List<ImageItem> image) {
        // save image list to favorite db
    }

    public static List<ImageItem> getFavorites() {
        // get all favorites from db
        return dataBase.getFavorites();
    }

}
