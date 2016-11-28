package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */
import java.util.LinkedList;
import java.util.List;

import com.skai.snapboard.Board;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BoardDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOARD_TABLE =  "CREATE TABLE boards ( " +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "filePath TEXT, "+
                                    "classification TEXT, "+
                                    "tag TEXT, "+
                                    "date TEXT, "+
                                    "latitude REAL, "+
                                    "longitude REAL )";

        // create books table
        db.execSQL(CREATE_BOARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS boards");

        // create fresh books table
        this.onCreate(db);
    }

    //--------------------------------------------------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) board + get all boards + delete all boards
     */

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

    private static final String[] COLUMNS = {KEY_ID,KEY_FILEPATH,KEY_CLASSIFICATION,KEY_TAG,KEY_DATE,KEY_LATITUDE,KEY_LONGITUDE};

    public void addBoard(Board board){
        Log.d("addBoard", board.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FILEPATH, board.getFilePath()); // get filePath
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

        Log.d("getBoard("+id+")", board.toString());

        // 5. return board
        return board;
    }

    // Get All Boards
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

    // Updating single board
    public int updateBoard(Board board) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("filePath", board.getFilePath()); // get filePath
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

    // Deleting single board
    public void deleteBoard(Board board) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_BOARDS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(board.getId()) });

        // 3. close
        db.close();

        Log.d("deleteBoard", board.toString());

    }
}