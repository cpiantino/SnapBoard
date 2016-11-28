package com.skai.snapboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import com.skai.snapboard.MySQLiteHelper;

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
        this.longitude = longitude;
    }

    // Boards table name
    private static final String TABLE_BOARDS = "boards";

    // Boards Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FILEPATH = "filePath";
    private static final String KEY_CLASSIFICATION = "classification";
    private static final String KEY_TAG = "tag";
    private static final String KEY_DATE = "date";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private static final String[] COLUMNS = {KEY_ID,KEY_FILEPATH, KEY_CLASSIFICATION, KEY_TAG, KEY_DATE, KEY_LATITUDE, KEY_LONGITUDE};

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
