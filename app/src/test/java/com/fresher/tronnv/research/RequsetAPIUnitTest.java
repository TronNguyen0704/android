package com.fresher.tronnv.research;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.model.MusicLyric;
import com.fresher.tronnv.research.network.RequestLyricInterface;
import com.fresher.tronnv.research.network.RetrofitClient;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequsetAPIUnitTest {
    @Test
    public void retroClient1_isNotNull() {
        assertNotNull(RetrofitClient.getClientRxJava(Utils.BASE_URL));
    }
    @Test
    public void retroClient2_isNotNull() {
        assertNotNull(RetrofitClient.getClient(Utils.BASE_URL));
    }
    @Test
    public void requestLyricInterface_isNotNull(){
        assertNotNull(RetrofitClient.getClient(Utils.BASE_URL).create(RequestLyricInterface.class));
    }
    @Test
    public void requestLyricInterface2_isNotNull(){
        assertNotNull(RetrofitClient.getClientRxJava(Utils.BASE_URL).create(RequestLyricInterface.class));
    }
}