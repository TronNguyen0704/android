package com.fresher.tronnv.android_models;

public class DataType {
    private int mType;
    private String mName;
    private boolean mState;
    public DataType(int type, String name){
        mType = type;
        mName = name;
    }
    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public boolean isState() {
        return mState;
    }

    public void setState(boolean mState) {
        this.mState = mState;
    }

    @Override
    public boolean equals(Object obj) {
        DataType dataType = (DataType) obj;
       return dataType.getName().endsWith(mName) &&
               (dataType.getType() == mType) &&
               dataType.isState() == mState;
    }
}
