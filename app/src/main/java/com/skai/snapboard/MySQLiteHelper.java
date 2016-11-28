package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

}