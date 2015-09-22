package com.todor.imageview.model;

import java.io.Serializable;

public class ImageItem implements Serializable {

    private String path;
    private String name;
    private float weight;
    private int width, height;
    private String date;
    private boolean isFavorite;
    private int resultIndex;

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
