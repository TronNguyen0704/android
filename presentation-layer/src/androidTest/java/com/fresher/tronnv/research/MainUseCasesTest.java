package com.fresher.tronnv.research;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fresher.tronnv.domain_layer.base.MusicBusiness;
import com.fresher.tronnv.domain_layer.impl.MusicBusinessImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainUseCasesTest {
    private MusicBusiness musicBusiness;
    @Before
    public void setUp(){
        musicBusiness = new MusicBusinessImpl();
    }
    @After
    public void tearDown(){
        musicBusiness = null;
    }
    @Test
    public void getMusic(){
        assertNotNull(musicBusiness.getAllMusicData());
    }
    @Test
    public void getMusicByName(){
        assertNotNull(musicBusiness.getMusicByName("null"));
    }
    @Test
    public void getSongByIdTrue(){
        assertNotNull(musicBusiness.getSongById(2));
    }
    @Test
    public void getSongByIdFalse(){
        assertNull(musicBusiness.getSongById(-1));
    }
}
