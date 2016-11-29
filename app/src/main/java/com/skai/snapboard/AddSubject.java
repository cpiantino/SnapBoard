package com.skai.snapboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddSubject extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
    }


    // Salvar Quadro no BD
    public void insertData(View view) {
        EditText tagEditText = (EditText)findViewById(R.id.tagEditText);
    }

    // Return Home Method
    public void returnHome(View view) {
        returnHome((Subject)null);
    }
    private void returnHome(Subject subject) {
        Intent addSubjectIntent = new Intent(AddSubject.this, Home.class);
        addSubjectIntent.putExtra("newSubject", subject);
        AddSubject.this.startActivity(addSubjectIntent);
    }
}
