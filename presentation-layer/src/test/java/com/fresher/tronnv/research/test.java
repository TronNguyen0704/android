package com.fresher.tronnv.research;

-----------------------------------com.vnt.Data.java-----------------------------------

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("lastIndex")
    @Expose
    private Integer lastIndex;
    @SerializedName("isMore")
    @Expose
    private Boolean isMore;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }

    public Boolean getIsMore() {
        return isMore;
    }

    public void setIsMore(Boolean isMore) {
        this.isMore = isMore;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
-----------------------------------com.vnt.Item.java-----------------------------------

public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("artId")
    @Expose
    private String artId;
    @SerializedName("art")
    @Expose
    private String art;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lrcId")
    @Expose
    private String lrcId;
    @SerializedName("adsDis")
    @Expose
    private Boolean adsDis;
    @SerializedName("isOfficial")
    @Expose
    private Boolean isOfficial;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("lrc")
    @Expose
    private String lrc;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("hasVideo")
    @Expose
    private Boolean hasVideo;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("gId")
    @Expose
    private String gId;
    @SerializedName("gName")
    @Expose
    private String gName;
    @SerializedName("cName")
    @Expose
    private String cName;
    @SerializedName("releaseDate")
    @Expose
    private Object releaseDate;
    @SerializedName("contentOwner")
    @Expose
    private String contentOwner;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("dwnStatus")
    @Expose
    private Integer dwnStatus;
    @SerializedName("streamingStatus")
    @Expose
    private Integer streamingStatus;
    @SerializedName("isWorld")
    @Expose
    private Boolean isWorld;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("hasDwnType")
    @Expose
    private String hasDwnType;
    @SerializedName("modifiedDate")
    @Expose
    private Integer modifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLrcId() {
        return lrcId;
    }

    public void setLrcId(String lrcId) {
        this.lrcId = lrcId;
    }

    public Boolean getAdsDis() {
        return adsDis;
    }

    public void setAdsDis(Boolean adsDis) {
        this.adsDis = adsDis;
    }

    public Boolean getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Boolean getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(Boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getGId() {
        return gId;
    }

    public void setGId(String gId) {
        this.gId = gId;
    }

    public String getGName() {
        return gName;
    }

    public void setGName(String gName) {
        this.gName = gName;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public Object getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Object releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContentOwner() {
        return contentOwner;
    }

    public void setContentOwner(String contentOwner) {
        this.contentOwner = contentOwner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDwnStatus() {
        return dwnStatus;
    }

    public void setDwnStatus(Integer dwnStatus) {
        this.dwnStatus = dwnStatus;
    }

    public Integer getStreamingStatus() {
        return streamingStatus;
    }

    public void setStreamingStatus(Integer streamingStatus) {
        this.streamingStatus = streamingStatus;
    }

    public Boolean getIsWorld() {
        return isWorld;
    }

    public void setIsWorld(Boolean isWorld) {
        this.isWorld = isWorld;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHasDwnType() {
        return hasDwnType;
    }

    public void setHasDwnType(String hasDwnType) {
        this.hasDwnType = hasDwnType;
    }

    public Integer getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Integer modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
-----------------------------------com.vnt.Music.java-----------------------------------

public class Music {

    @SerializedName("err")
    @Expose
    private Integer err;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public Integer getErr() {
        return err;
    }

    public void setErr(Integer err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}