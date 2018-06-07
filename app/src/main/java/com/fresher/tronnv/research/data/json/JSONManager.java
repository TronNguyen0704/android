package com.fresher.tronnv.research.data.json;


import com.fresher.tronnv.research.data.source.RecordChart;
import com.fresher.tronnv.research.data.source.Track;
import com.fresher.tronnv.research.model.MusicLyric;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class JSONManager {
    public static List<MusicLyric> JsonSimpleReader(){
        CharBuffer charBuffer = CharBuffer.allocate(100000);
        try {
            FileReader file = new FileReader("/data/data/com.fresher.tronnv.research/data.json");
            file.read(charBuffer);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        List<MusicLyric> list = new ArrayList<>();
        String test = String.copyValueOf(charBuffer.array());
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(!jsonObject.isNull(i)){
            try {
                list.add(gson.fromJson(jsonObject.getString(i), MusicLyric.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        return list;
    }
    public static boolean isExist(){
        File file = new File("/data/data/com.fresher.tronnv.research/data.json");
        return file.exists();
    }
    public static void JsonSimpleWriter(List<MusicLyric> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        try (FileWriter file = new FileWriter( "/data/data/com.fresher.tronnv.research/data.json")) {
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(json);
    }
    public static void JsonRecordChartWriter(List<RecordChart> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        try (FileWriter file = new FileWriter( "/data/data/com.fresher.tronnv.research/data-rank.json")) {
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(json);
    }
    public static List<RecordChart> JsonRecordChartReader() {
        CharBuffer charBuffer = CharBuffer.allocate(100000);
        try {
            FileReader file = new FileReader("/data/data/com.fresher.tronnv.research/data-rank.json");
            file.read(charBuffer);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        List<RecordChart> list = new ArrayList<>();
        String test = String.copyValueOf(charBuffer.array());
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(!jsonObject.isNull(i)){
            try {
                list.add(gson.fromJson(jsonObject.getString(i), RecordChart.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        return list;
    }

    public static void JsonTrackWriter(List<Track> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        try (FileWriter file = new FileWriter( "/data/data/com.fresher.tronnv.research/data-track.json")) {
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(json);
    }
    public static List<Track> JsonTrackReader() {
        CharBuffer charBuffer = CharBuffer.allocate(100000);
        try {
            FileReader file = new FileReader("/data/data/com.fresher.tronnv.research/data-track.json");
            file.read(charBuffer);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        List<Track> list = new ArrayList<>();
        String test = String.copyValueOf(charBuffer.array());
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(!jsonObject.isNull(i)){
            try {
                list.add(gson.fromJson(jsonObject.getString(i), Track.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        return list;
    }
}
