package com.fresher.tronnv.android_data_layer;

import com.fresher.tronnv.android_models.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Constants {
    static final String BASE_URL = "http://www.mocky.io/v2/";
    public static List<DataType> generatorDataType(){
        List<DataType> items = new ArrayList<>();
        for(int i = 0 ;i < 100;i++){
            items.add(new DataType(i,"TYPE_" + i));
        }
        return items;
    }
    public static List<DataType> generatorDataType2(){
        List<DataType> items = new ArrayList<>();
        for(int i = 0 ;i < 100;i++){
            items.add(new DataType(i,"TYPE_" + i));
            if(i % 2 == 0){
                items.get(i).setState(true);
            }
        }
        return items;
    }
    public static List<String> sImages(){
        List<String> mImages = new ArrayList<>();
        mImages.add("https://zmp3-photo.zadn.vn/covers/e/9/e92910699de0aeee9f1587e0425d8a7c_1514894974.png");
        mImages.add("https://zmp3-photo.zadn.vn/covers/c/5/c5360ad3d2e28b5bb5f0d0930a6a6a6f_1499827454.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/9/5/95488ad8d45bd69ef83e9403c4114375_1499827707.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/7/0/702ed1b745f1b326f4fc07e8b60afea4_1499828300.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/cover/5/5/5/e/555e1711704ed04400cce99ffbccc8b1.jpg");
        mImages.add("https://zmp3-photo.zadn.vn/covers/d/1/d1c2738deec7efd1942a3027a1c436b0_1499828277.jpg");
        return mImages;
    }
}