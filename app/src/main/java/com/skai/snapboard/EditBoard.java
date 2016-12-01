package com.skai.snapboard;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditBoard extends AppCompatActivity {

    // Received Board to Edit
    private Board editBoard;
    private Bitmap mImageBitmap;
    private ImageView mImageView;

    // Variables for editing Board
    private EditText subjectText;
    private EditText tagText;
    private TextView locationText;
    private TextView dateText;
    // ------------------------
    private int id;
    private String filepath;
    private String subject;
    private String tag;
    private String date;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        editBoard = (Board)getIntent().getSerializableExtra("editBoard");
        startBoardEdit();
    }



    //------------------------------Capturar Quadro e Processar Imagem--------------------------
    //------------------------------------------------------------------------------------------
    @TargetApi(24)
    private void startBoardEdit() {
        try {
            mImageView = (ImageView) findViewById(R.id.boardView);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            mImageBitmap = BitmapFactory.decodeFile(editBoard.getFilePath(),options);

            float aspectRatio = mImageBitmap.getWidth() / (float) mImageBitmap.getHeight();
            int width = 480;
            int height = Math.round(width / aspectRatio);
            mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, width, height, false);

            System.out.println(mImageBitmap==null?"null":"bit exists");

            mImageView.setImageBitmap(mImageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        subjectText = (EditText) findViewById(R.id.subjectEditText);
        tagText = (EditText) findViewById(R.id.tagEditText);
        locationText = (TextView) findViewById(R.id.locationTextView);
        dateText = (TextView) findViewById(R.id.dateTextView);

        subjectText.setText(editBoard.getSubject());
        tagText.setText(editBoard.getTag());
        dateText.setText(editBoard.getDate());
        locationText.setText("Lat:"+Double.toString(editBoard.getLatitude())+"\nLon:"+Double.toString(editBoard.getLongitude()));
    }

    // Salvar Quadro no BD
    public void saveData(View view) {
        id = editBoard.getId();
        filepath = editBoard.getFilePath();
        subject = subjectText.getText().toString();
        tag = tagText.getText().toString();
        date = dateText.getText().toString();
        latitude = editBoard.getLatitude();
        longitude = editBoard.getLongitude();

        saveData(id, filepath, subject, tag, date, latitude, longitude);
    }
    private void saveData(int id, String photo, String subject, String tag, String date, Double latitude, Double longitude) {
        Board board = new Board(id, photo, subject, tag, date, latitude, longitude);
        saveData(board);
    }

    // Save / Edit Board Call
    private void saveData(Board board) {
        Intent addBoardIntent = new Intent(EditBoard.this, Home.class);
        addBoardIntent.putExtra("editBoard", board);
        EditBoard.this.startActivity(addBoardIntent);
    }

    // Delete Board Call
    public void deleteData(View view) {
        Intent addBoardIntent = new Intent(EditBoard.this, Home.class);
        addBoardIntent.putExtra("deleteBoard", editBoard);
        EditBoard.this.startActivity(addBoardIntent);
    }
}
