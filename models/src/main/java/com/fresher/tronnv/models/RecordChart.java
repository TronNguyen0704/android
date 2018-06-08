package com.fresher.tronnv.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RecordChart {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("rank")
    @Expose
    private int rank;
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getRank() {
        return rank;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }




    public RecordChart(){}
    public RecordChart( Long mid,  String name, String author,
                        String avatar, int rank) {
        this.id = mid;
        this.name = name;
        this.author = author;
        this.avatar = avatar;
        this.rank = rank;
    }
    public boolean isEmpty() {
        return (name == null || name.endsWith("") )&& ( avatar== null || avatar.endsWith(""));
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
        return id.equals(task.id) &&
                name.endsWith(task.name) &&
                avatar.endsWith(task.avatar);
    }
}
