package com.company.modelviewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addnote extends AppCompatActivity {

    Button save;
    Button cancel;
    EditText title;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        getSupportActionBar().setTitle("Add New Note"); // set the top title

        save = findViewById(R.id.buttonSave2);
        cancel = findViewById(R.id.buttonCancel2);
        title = findViewById(R.id.editTextTitle2);
        description = findViewById(R.id.editTextDesc2);
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveData() {

        String note_title = title.getText().toString();
        String note_description = description.getText().toString();
        Intent data = new Intent();
        data.putExtra("not_title", note_title);
        data.putExtra("note_description", note_description);
        setResult(RESULT_OK, data);
        finish();
    }
}
