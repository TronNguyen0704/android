package com.fresher.tronnv.android_models;

public class ThirdBody {
    private String mTitle;
    private String mAuthor;
    private int mViews;
    public ThirdBody(String title, String author){
        mAuthor = author;
        mTitle = title;
        mViews = 124;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getViews() {
        return mViews;
    }

    public void setViews(int mViews) {
        this.mViews = mViews;
    }
}
