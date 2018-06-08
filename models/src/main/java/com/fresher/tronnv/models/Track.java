package com.fresher.tronnv.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;


public class Track {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumb")
    @Expose
    private String thumb;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }



    public Track(){}
    public Track(Long mid,  String title, String description,
                       String thumb) {
        this.id = mid;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
    }
    public boolean isEmpty() {
        return title==null && thumb == null;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(new Track(id, title, description, thumb));
    }

    @Override
    public String toString() {
        return title;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track item = (Track) o;
        return id.equals(item.id) &&
                title.endsWith( item.title) &&
               thumb.endsWith( item.thumb);
    }
}
