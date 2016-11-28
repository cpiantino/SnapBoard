package com.skai.snapboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

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

    public void addBoard(Board board){
        //for logging
        Log.d("addBoard", board.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FILEPATH, board.getFilePath()); // get filepath
        values.put(KEY_CLASSIFICATION, board.getClassification()); // get classification
        values.put(KEY_TAG, board.getTag()); // get tag
        values.put(KEY_DATE, board.getDate()); // get date
        values.put(KEY_LATITUDE, board.getLatitude()); // get latitude
        values.put(KEY_LONGITUDE, board.getLongitude()); // get longitude

        // 3. insert
        db.insert(TABLE_BOARDS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Board getBoard(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_BOARDS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build board object
        Board board = new Board();
        board.setId(Integer.parseInt(cursor.getString(0)));
        board.setFilePath(cursor.getString(1));
        board.setClassification(cursor.getString(2));
        board.setTag(cursor.getString(3));
        board.setDate(cursor.getString(4));
        board.setLatitude(Double.parseDouble(cursor.getString(5)));
        board.setLongitude(Double.parseDouble(cursor.getString(6)));

        //log
        Log.d("getBoard("+id+")", board.toString());

        // 5. return board
        return board;
    }

    public List<Board> getAllBoards() {
        List<Board> boards = new LinkedList<Board>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOARDS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build board and add it to list
        Board board = null;
        if (cursor.moveToFirst()) {
            do {
                board = new Board();
                board.setId(Integer.parseInt(cursor.getString(0)));
                board.setFilePath(cursor.getString(1));
                board.setClassification(cursor.getString(2));
                board.setTag(cursor.getString(3));
                board.setDate(cursor.getString(4));
                board.setLatitude(Double.parseDouble(cursor.getString(5)));
                board.setLongitude(Double.parseDouble(cursor.getString(6)));

                // Add board to boards
                boards.add(board);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBoards()", boards.toString());

        // return boards
        return boards;
    }

    public int updateBoard(Board board) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("filePath", board.getFilePath()); // get filepath
        values.put("classification", board.getClassification()); // get classification
        values.put("tag", board.getTag()); // get tag
        values.put("date", board.getDate());
        values.put("latitude", board.getLatitude());
        values.put("longitude", board.getLongitude());

        // 3. updating row
        int i = db.update(TABLE_BOARDS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(board.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteBoard(Board board) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOARDS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(board.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBoard", board.toString());

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
