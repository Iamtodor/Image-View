package com.todor.imageview.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.todor.imageview.utils.DataBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GalleryImages {
    static DataBase dataBase;

    public static List<ImageItem> getImageFromFolder(String path, Activity activity) {
        dataBase = new DataBase(activity);
        ArrayList<ImageItem> favoriteImageItems = dataBase.getFavorites();
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        File[] listFile;
        File file = new File(android.os.Environment.getExternalStorageDirectory(), path);
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                ImageItem imageItem = new ImageItem();
                imageItem.setPath(listFile[i].getAbsolutePath());
                String fullPath = listFile[i].getAbsolutePath();
                String[] name = fullPath.split("/");
                long date = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                imageItem.setDate(String.valueOf(simpleDateFormat.format(date)));
                imageItem.setName(name[name.length - 1]);
                for (ImageItem favoriteItem : favoriteImageItems) {
                    if (favoriteItem.getPath().equals(imageItem.getPath()))
                        imageItem.setFavorite(true);
                }
                imageItems.add(imageItem);
            }
        }
        return imageItems;
    }

    public static void saveOrDeleteFavorite(ImageItem imageItem) {
        if (imageItem.isFavorite()) {
            imageItem.setFavorite(false);
            dataBase.deleteFavorite(imageItem);
        } else {
            imageItem.setFavorite(true);
            dataBase.saveFavorite(imageItem);
        }
    }

    public static void saveOrDeleteFavoriteFromSearch(ImageItem imageItem, Bitmap bitmap) {
        if (imageItem.isFavorite()) {
            imageItem.setFavorite(false);
            dataBase.deleteFavoriteFromSearch(imageItem);
        } else {
            imageItem.setFavorite(true);
            dataBase.saveFavoriteFromSearch(imageItem, bitmap);
        }
    }

    public static List<ImageItem> getFavorites() {
        return dataBase.getFavorites();
    }

}
