package com.fresher.tronnv.research;

import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

public class Utils {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    public static RequestLyricInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(RequestLyricInterface.class);
    }
}
