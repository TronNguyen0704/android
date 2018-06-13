package com.fresher.tronnv.android_models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.common.base.Strings;

import java.util.Objects;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "ranks")
public class RecordChart {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;
    @Nullable
    public Long getId() {
        return id;
    }
    @Nullable
    public String getName() {
        return name;
    }
    @Nullable
    public String getAuthor() {
        return author;
    }
    @Nullable
    public String getAvatar() {
        return avatar;
    }
    @Nullable
    public int getRank() {
        return rank;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    @Nullable
    @ColumnInfo(name = "name")
    private String name;

    @Nullable
    @ColumnInfo(name = "author")
    private String author;

    @Nullable
    @ColumnInfo(name = "avatar")
    private String avatar;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Nullable

    @ColumnInfo(name = "rank")
    private int rank;


    public RecordChart(){}
    public RecordChart(@Nullable Long mid, @Nullable String name, @Nullable String author,
                       @Nullable String avatar, int rank) {
        this.id = mid;
        this.name = name;
        this.author = author;
        this.avatar = avatar;
        this.rank = rank;
    }
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(name) &&
                Strings.isNullOrEmpty(avatar);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(new RecordChart(id, name, author, avatar, rank));
    }

    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordChart task = (RecordChart) o;
        return com.google.common.base.Objects.equal(id, task.id) &&
                com.google.common.base.Objects.equal(name, task.name) &&
                com.google.common.base.Objects.equal(avatar, task.avatar);
    }
}