package com.todor.imageview.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.todor.imageview.model.ImageItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImageItems";
    private static final String IMAGES_TABLE = "images_table";

    private static final int DATABASE_VERSION = 1;
    private static final String PATH = "path";
    private static final String NAME = "name";
    private static final String WEIGHT = "weight";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String BYTE = "byte";

    private static final String DATE = "date";

    private static SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + IMAGES_TABLE + "(" + PATH +
                " TEXT PRIMARY KEY," + NAME + " TEXT," + WEIGHT + " REAL," + WIDTH + " INTEGER," +
                HEIGHT + " INTEGER," + DATE + " TEXT, " + BYTE + " BLOB" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void saveFavoriteFromSearch(ImageItem imageItem, Bitmap bitmap) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PATH, imageItem.getPath());
        cv.put(NAME, imageItem.getName());
        cv.put(DATE, imageItem.getDate());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        cv.put(BYTE, stream.toByteArray());
        db.insert(IMAGES_TABLE, null, cv);
    }

    public void deleteFavoriteFromSearch(ImageItem imageItem) {
        db = getWritableDatabase();
        db.delete(IMAGES_TABLE, PATH + "=?", new String[]{imageItem.getPath()});
        close();
    }

    public void saveFavorite(ImageItem imageItem) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PATH, imageItem.getPath());
        cv.put(NAME, imageItem.getName());
        cv.put(DATE, String.valueOf(imageItem.getDate()));
        db.insert(IMAGES_TABLE, null, cv);
        close();
    }

    public void deleteFavorite(ImageItem imageItem) {
        db = getWritableDatabase();
        db.delete(IMAGES_TABLE, PATH + "=?", new String[]{imageItem.getPath()});
        close();
    }

    public ArrayList<ImageItem> getFavorites() {
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + IMAGES_TABLE;
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ImageItem imageItem = new ImageItem();
                imageItem.setPath(cursor.getString(cursor.getColumnIndex(PATH)));
                imageItem.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                imageItem.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                imageItem.setFavorite(true);
                if(cursor.getBlob(cursor.getColumnIndex(BYTE)) != null) {
                    imageItem.setBitmap(BitmapFactory.decodeByteArray(
                            cursor.getBlob(cursor.getColumnIndex(BYTE)), 0,
                            cursor.getBlob(cursor.getColumnIndex(BYTE)).length));
                }
                imageItems.add(imageItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return imageItems;
    }
}
