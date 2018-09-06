package com.fresher.tronnv.android_models;

public class SecondBody {
    private String mPhotoRes;
    private String mTitle;
    private String[] mPlaylist;
    public SecondBody(String title,String res){
        mPhotoRes = res;
        mTitle = title;
    }

    public String getPhotoRes() {
        return mPhotoRes;
    }

    public void setPhotoRes(String res) {
        this.mPhotoRes = res;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String[] getPlaylist() {
        return mPlaylist;
    }

    public void setPlaylist(String[] mPlaylist) {
        this.mPlaylist = mPlaylist;
    }
}
