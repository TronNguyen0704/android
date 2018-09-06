package com.fresher.tronnv.android_domain_layer;

import com.fresher.tronnv.android_data_layer.Constants;
import com.fresher.tronnv.android_models.DataType;

import java.util.List;

public class DataTypeBusiness {
    private DataTypeBusiness(){
    }
    public static List<DataType> getDataTypes(){
        return Constants.generatorDataType();
    }
    public static List<DataType> getDataTypes2(){
        return Constants.generatorDataType2();
    }
    public static List<String> getImages(){return Constants.sImages();}
}
