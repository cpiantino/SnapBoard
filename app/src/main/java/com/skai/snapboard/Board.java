package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */
public class Board {
    private int id;
    private String filePath;
    private String subject;
    private String tag;
    private String date;
    private double latitude;
    private double longitude;

    public Board(){}

    public Board(String filePath, String subject, String tag, String date, double latitude, double longitude) {
        super();
        this.filePath = filePath;
        this.subject = subject;
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
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_TAG = "tag";
    private static final String KEY_DATE = "date";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    private static final String[] COLUMNS = {KEY_ID,KEY_FILEPATH, KEY_SUBJECT, KEY_TAG, KEY_DATE, KEY_LATITUDE, KEY_LONGITUDE};

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", subject='" + subject + '\'' +
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
