package com.fresher.tronnv.android_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.common.base.Strings;

import java.util.Objects;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */
@Entity(tableName = "songs")
public class MusicLyric {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "lyricId")
    private Integer lyricId;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "author")
    private String author;
    @NonNull
    @ColumnInfo(name = "lyric")
    private String lyric;
    @NonNull
    @ColumnInfo(name = "avatar")
    private String avatar;
    @Nullable
    public Integer getId() {
        return id;
    }
    @Nullable
    public void setId(Integer id) {
        this.id = id;
    }
    @Nullable
    public Integer getLyricId() {
        return lyricId;
    }
    @Nullable
    public void setLyricId(Integer lyricId) {
        this.lyricId = lyricId;
    }
    @Nullable
    public String getName() {
        return name;
    }
    @Nullable
    public void setName(String name) {
        this.name = name;
    }
    @Nullable
    public String getAuthor() {
        return author;
    }
    @Nullable
    public void setAuthor(String author) {
        this.author = author;
    }
    @Nullable
    public String getLyric() {
        return lyric;
    }
    @Nullable
    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
    @Nullable
    public String getAvatar() {
        return avatar;
    }
    @Nullable
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