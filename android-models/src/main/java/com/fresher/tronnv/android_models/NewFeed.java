package com.fresher.tronnv.android_models;

public class NewFeed {
    private int mPhotoResId;
    private int mAvatarResId;
    private String mContent;
    private String mAdmin;
    private int mTime;
    private String mDetail;
    private String mType;
    private int mLikes;
    private int mViews;
    public NewFeed(int avatarResId, String admin) {
        mAvatarResId = avatarResId;
        mAdmin = admin;
    }
    public int getPhotoResId() {
        return mPhotoResId;
    }

    public void setPhotoResId(int mPhotoResId) {
        this.mPhotoResId = mPhotoResId;
    }

    public int getAvatarResId() {
        return mAvatarResId;
    }

    public void setAvatarResId(int mAvatarResId) {
        this.mAvatarResId = mAvatarResId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getAdmin() {
        return mAdmin;
    }

    public void setAdmin(String mAdmin) {
        this.mAdmin = mAdmin;
    }
    public int getTime() {
        return mTime;
    }

    public void setTime(int mTime) {
        this.mTime = mTime;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String mAuthor) {
        this.mDetail = mAuthor;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int mLikes) {
        this.mLikes = mLikes;
    }

    public int getViews() {
        return mViews;
    }

    public void setViews(int mViews) {
        this.mViews = mViews;
    }
}
