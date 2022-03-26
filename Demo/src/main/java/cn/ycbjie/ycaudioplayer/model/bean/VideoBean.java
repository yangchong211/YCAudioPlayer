package cn.ycbjie.ycaudioplayer.model.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * <pre>
 *     @author yangchong
 *     blog  : www.pedaily.cn
 *     time  : 2018/03/22
 *     desc  : 视频信息
 *     revise:
 * </pre>
 */

public class VideoBean implements Serializable {

    // 视频类型:本地/网络
    private Type type;
    // [本地视频]视频id
    private long id;
    // 视频标题
    private String title;
    // [本地视频]专辑ID
    private long albumId;
    // [在线视频]专辑封面路径
    private String coverPath;
    // 持续时间
    private long duration;
    // 视频路径
    private String path;
    // 文件名
    private String fileName;
    // 文件大小
    private long fileSize;
    // 分辨率
    private String resolution;
    // 修改时间
    private long date;
    // 封面缩略图
    private Bitmap videoThumbnail;

    public enum Type {
        /**
         * 本地的
         */
        LOCAL,
        /**
         * 在线的
         */
        ONLINE
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Bitmap getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(Bitmap videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    /**
     * 对比本地视频是否相同
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof VideoBean && this.getId() == ((VideoBean) o).getId();
    }
}
