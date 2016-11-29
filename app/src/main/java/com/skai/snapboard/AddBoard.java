package com.skai.snapboard;

import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AddBoard extends AppCompatActivity {

    // Variables for image grab and bitmap display
    Board board;
    Uri imageFile;
    ImageView imageView;
    Bitmap help1;
    ThumbnailUtils thumbnail;
    String mCurrentPhotoPath;

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
    }
}
