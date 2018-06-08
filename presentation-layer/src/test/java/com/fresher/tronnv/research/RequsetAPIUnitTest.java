package com.fresher.tronnv.research;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequsetAPIUnitTest {
    @Test
    public void retroClient1_isNotNull() {
        assertNotNull(RetrofitClient.getClientRxJava(Constants.BASE_URL));
    }
    @Test
    public void retroClient2_isNotNull() {
        assertNotNull(RetrofitClient.getClient(Constants.BASE_URL));
    }
    @Test
    public void requestLyricInterface_isNotNull(){
        assertNotNull(RetrofitClient.getClient(Constants.BASE_URL).create(RequestLyricInterface.class));
    }
    @Test
    public void requestLyricInterface2_isNotNull(){
        assertNotNull(RetrofitClient.getClientRxJava(Constants.BASE_URL).create(RequestLyricInterface.class));
    }
}