package com.fresher.tronnv.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
public class MusicLyric {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lyricId")
    @Expose
    private Integer lyricId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("lyric")
    @Expose
    private String lyric;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLyricId() {
        return lyricId;
    }

    public void setLyricId(Integer lyricId) {
        this.lyricId = lyricId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    @Override
    public String toString() {
        return "{\"id\": " + id +
                ",\"name\": \"" + name +
                "\",\"author\": \"" + author +
                "\",\"lyric\": \"" + lyric +
                "\",\"avatar\": \"" + avatar +
                "\",\"lyricId\": " + lyricId + "}";
    }
}