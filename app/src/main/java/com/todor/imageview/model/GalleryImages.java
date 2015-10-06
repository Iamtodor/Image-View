package com.todor.imageview.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.todor.imageview.utils.DataBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                if(getMimeType(listFile[i].getAbsolutePath())) {
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
        }
        if(imageItems.size() == 0) {
            Toast.makeText(activity, "This folder has no image", Toast.LENGTH_SHORT).show();
        }
        return imageItems;
    }

    public static boolean getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if("image/jpeg".equals(type)) {
                return true;
            }
        }
        return false;
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
