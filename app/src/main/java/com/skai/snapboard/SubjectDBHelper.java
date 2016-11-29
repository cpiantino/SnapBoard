package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class SubjectDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SubjectDB";

    public SubjectDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_SUBJECT_TABLE =  "CREATE TABLE subjects ( " +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "subject TEXT, "+
                                    "day TEXT, "+
                                    "start TEXT, "+
                                    "end TEXT )";

        // create books table
        db.execSQL(CREATE_SUBJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS subjects");

        // create fresh books table
        this.onCreate(db);
    }

    //--------------------------------------------------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", upstart, delete) subject + get all subjects + delete all subjects
     */

    // Subjects table name
    private static final String TABLE_SUBJECTS = "subjects";

    // Subjects Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_DAY = "day";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";

    private static final String[] COLUMNS = {KEY_ID,KEY_SUBJECT,KEY_DAY,KEY_START,KEY_END};

    public void addSubject(Subject subject){
        Log.d("addSubject", subject.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT, subject.getSubject()); // get subject
        values.put(KEY_DAY, subject.getDay()); // get day
        values.put(KEY_START, subject.getStart()); // get start
        values.put(KEY_END, subject.getEnd()); // get end

        // 3. insert
        db.insert(TABLE_SUBJECTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Subject getSubject(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_SUBJECTS, // a. table
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

        // 4. build subject object
        Subject subject = new Subject();
        subject.setId(Integer.parseInt(cursor.getString(0)));
        subject.setSubject(cursor.getString(1));
        subject.setDay(cursor.getString(2));
        subject.setStart(cursor.getString(3));
        subject.setEnd(cursor.getString(4));

        Log.d("getSubject("+id+")", subject.toString());

        // 5. return subject
        return subject;
    }

    // Get All Subjects
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new LinkedList<Subject>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SUBJECTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build subject and add it to list
        Subject subject = null;
        if (cursor.moveToFirst()) {
            do {
                subject = new Subject();
                subject.setId(Integer.parseInt(cursor.getString(0)));
                subject.setSubject(cursor.getString(1));
                subject.setDay(cursor.getString(2));
                subject.setStart(cursor.getString(3));
                subject.setEnd(cursor.getString(4));

                // Add subject to subjects
                subjects.add(subject);
            } while (cursor.moveToNext());
        }

        Log.d("getAllSubjects()", subjects.toString());

        // return subjects
        return subjects;
    }

    // Updating single subject
    public int upstartSubject(Subject subject) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("subject", subject.getSubject()); // get subject
        values.put("day", subject.getDay()); // get day
        values.put("start", subject.getStart());
        values.put("end", subject.getEnd());

        // 3. updating row
        int i = db.update(TABLE_SUBJECTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(subject.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single subject
    public void deleteSubject(Subject subject) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_SUBJECTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(subject.getId()) });

        // 3. close
        db.close();

        Log.d("deleteSubject", subject.toString());

    }
}