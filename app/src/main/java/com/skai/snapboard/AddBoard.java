package com.skai.snapboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AddBoard extends AppCompatActivity {

    // Variables for image grab and bitmap display
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "AddBoard";
    private boolean captureComplete = false;

    // Image tag and subject
    private String tag = "TAG";
    private String subject = "SUBJECT";

    // Variables for acquiring location
    private LocationManager senLocationManager;
    private Location location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    String currentDateTimeString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        LocationManager senLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = senLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (SecurityException e) {
        }

        startBoardCapture();
    }



    //------------------------------Capturar Quadro e Processar Imagem--------------------------
    //------------------------------------------------------------------------------------------
    @TargetApi(24)
    private void startBoardCapture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i(TAG, "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

        try {
            LocationManager senLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = senLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException secErr) {
            Log.i(TAG, "Security Exception");
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        TextView latitudeText = (TextView) findViewById(R.id.locationTextView);
        TextView dataText = (TextView) findViewById(R.id.dateTextView);

        latitudeText.setText("Lat:"+Double.toString(latitude)+"\nLon:"+Double.toString(longitude));
        dataText.setText(currentDateTimeString);
    }

    // Get path absoluto da imagem
    @TargetApi(24)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        captureComplete = true;
        return image;
    }

    // Processar imagem e colocar bitmap pra visualização
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageView = (ImageView) findViewById(R.id.boardView);
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));

                float aspectRatio = mImageBitmap.getWidth() / (float) mImageBitmap.getHeight();
                int width = 480;
                int height = Math.round(width / aspectRatio);
                mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, width, height, false);

                mImageView.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Salvar Quadro no BD
    public void saveData(View view) {
        if (mCurrentPhotoPath!=null && captureComplete) saveData(mCurrentPhotoPath, latitude, longitude, currentDateTimeString);
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(AddBoard.this).create();
            alertDialog.setTitle("Não há quadro!");
            alertDialog.setMessage("Tire uma foto do quadro primeiro para poder salvar :)");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
    private void saveData(String photo, double latitude, double longitude, String date) {
        Board board;
        EditText subjectEditText = (EditText)findViewById(R.id.subjectEditText);
        subject = subjectEditText.getText().toString();
        EditText tagEditText = (EditText)findViewById(R.id.tagEditText);
        tag = tagEditText.getText().toString();
        board = new Board(photo, subject, tag, date, latitude, longitude);
        captureComplete = false;
        deleteData(board);
    }

    // Return Home Method
    public void deleteData(View view) {
        deleteData((Board)null);
    }
    private void deleteData(Board board) {
        Intent addBoardIntent = new Intent(AddBoard.this, Home.class);
        addBoardIntent.putExtra("newBoard", board);
        AddBoard.this.startActivity(addBoardIntent);
    }
}
