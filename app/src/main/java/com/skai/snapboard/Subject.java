package com.skai.snapboard;

/**
 * Created by SKai on 2016/11/28.
 */
public class Subject {
    private int id;
    private String subject;
    private String day;
    private String start;
    private String end;

    public Subject(){}

    public Subject(String subject, String day, String start, String end) {
        super();
        this.subject = subject;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    // Subjects table name
    private static final String TABLE_SUBJECTS = "subjects";

    // Subjects Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_DAY = "day";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";

    private static final String[] COLUMNS = {KEY_ID,KEY_SUBJECT, KEY_SUBJECT, KEY_DAY, KEY_START, KEY_END};

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", day='" + day + '\'' +
                ", start='" + start + '\'' +
                ", end=" + end +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
