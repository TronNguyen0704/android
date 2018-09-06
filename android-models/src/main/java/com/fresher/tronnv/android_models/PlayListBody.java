package com.fresher.tronnv.android_models;

public class PlayListBody {
    private String mPhotoRes;
    private String mTitle;
    private String[] mPlaylist;
    public PlayListBody(String title, String res, String[] playlist){
        mPhotoRes = res;
        mTitle = title;
        mPlaylist = playlist;
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
