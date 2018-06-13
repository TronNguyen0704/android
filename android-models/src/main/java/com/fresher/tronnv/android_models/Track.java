package com.fresher.tronnv.android_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.common.base.Strings;

import java.util.Objects;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "tracks")
public class Track {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;
    @Nullable
    public Long getId() {
        return id;
    }
    @Nullable
    public String getTitle() {
        return title;
    }
    @Nullable
    public String getDescription() {
        return description;
    }
    @Nullable
    public String getThumb() {
        return thumb;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    @Nullable
    @ColumnInfo(name = "title")
    private String title;

    @Nullable
    @ColumnInfo(name = "description")
    private String description;

    @Nullable
    @ColumnInfo(name = "thumb")
    private String thumb;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }



    public Track(){}
    public Track(@Nullable Long mid, @Nullable String title, @Nullable String description,
                 @Nullable String thumb) {
        this.id = mid;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
    }
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title) &&
                Strings.isNullOrEmpty(thumb);
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
        return com.google.common.base.Objects.equal(id, item.id) &&
                com.google.common.base.Objects.equal(title, item.title) &&
                com.google.common.base.Objects.equal(thumb, item.thumb);
    }
}