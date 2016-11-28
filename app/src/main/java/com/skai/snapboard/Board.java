package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */
public class Board {
    private int id;
    private String filePath;
    private String classification;
    private String tag;
    private String date;
    private double latitude;
    private double longitude;

    public Board(){}

    public Board(String filePath, String classification, String tag, String date, double latitude, double longitude) {
        super();
        this.filePath = filePath;
        this.classification = classification;
        this.tag = tag;
        this.date = date;
        this.latitude = latitude;
        this. longitude = longitude;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", classification='" + classification + '\'' +
                ", tag='" + tag + '\'' +
                ", date='" + date + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
