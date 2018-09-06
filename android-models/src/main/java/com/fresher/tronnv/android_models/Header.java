package com.fresher.tronnv.android_models;

public class Header {
    private String mAdminName;
    private String mShareType;
    private int mShareTime;
    private String mAvatar;
    public Header(String adminName, String shareType, String avatar ){
        mAdminName = adminName;
        mShareType = shareType;
        mAvatar = avatar;
        mShareTime = 1;
    }
    public Header(){

    }
    public String getAdminName() {
        return mAdminName;
    }

    public void setAdminName(String mAdminName) {
        this.mAdminName = mAdminName;
    }

    public String getShareType() {
        return mShareType;
    }

    public void setShareType(String mShareType) {
        this.mShareType = mShareType;
    }

    public int getShareTime() {
        return mShareTime;
    }

    public void setShareTime(int mShareTime) {
        this.mShareTime = mShareTime;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }



}
