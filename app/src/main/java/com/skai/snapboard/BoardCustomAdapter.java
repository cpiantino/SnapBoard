package com.skai.snapboard;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by SKai on 2016/11/30.
 */
public class BoardCustomAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    // Default constructor
    public BoardCustomAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {

        File image = new File(cursor.getString(cursor.getColumnIndex(BoardDBHelper.KEY_FILEPATH)));
        Bitmap mBitmapInsurance;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16;
        mBitmapInsurance = BitmapFactory.decodeFile(image.getAbsolutePath(),options);

        ImageView imageView = (ImageView) view.findViewById(R.id.board);
        imageView.setImageBitmap(mBitmapInsurance);

        TextView subjectTextView = (TextView) view.findViewById(R.id.subject);
        String subject = cursor.getString( cursor.getColumnIndex(BoardDBHelper.KEY_SUBJECT ));
        subjectTextView.setText(subject);

        TextView tagTextView = (TextView) view.findViewById(R.id.tag);
        String tag = cursor.getString( cursor.getColumnIndex(BoardDBHelper.KEY_TAG ));
        tagTextView.setText(tag);

        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        String date = cursor.getString( cursor.getColumnIndex(BoardDBHelper.KEY_DATE ));
        dateTextView.setText(date);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // R.layout.list_row is your xml layout for each row
        return cursorInflater.inflate(R.layout.board_item, parent, false);
    }
}