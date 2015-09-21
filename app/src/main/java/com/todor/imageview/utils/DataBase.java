package com.todor.imageview.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.todor.imageview.model.ImageItem;

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

    private static final String DATE = "date";

    private static SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + IMAGES_TABLE + "(" + PATH +
                " TEXT PRIMARY KEY," + NAME + " TEXT," + WEIGHT + " REAL," + WIDTH + " INTEGER," +
                HEIGHT + " INTEGER," + DATE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void saveFavorite(ImageItem item) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PATH, item.getPath());
        cv.put(NAME, item.getName());
        cv.put(DATE, String.valueOf(item.getDate()));
        db.insert(IMAGES_TABLE, null, cv);
        close();
    }

    public void deleteFavorite(ImageItem item) {
        db = getWritableDatabase();
        db.delete(IMAGES_TABLE, PATH + "=?", new String[]{item.getPath()});
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
                imageItems.add(imageItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return imageItems;
    }
}
