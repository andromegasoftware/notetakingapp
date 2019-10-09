package com.company.modelviewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editnote extends AppCompatActivity {

    Button save;
    Button cancel;
    EditText title;
    EditText description;
    int id;
    String notTitle;
    String notDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        getSupportActionBar().setTitle("Edit Note"); // set the top title

        save = findViewById(R.id.buttonSave2);
        cancel = findViewById(R.id.buttonCancel2);
        title = findViewById(R.id.editTextTitle2);
        description = findViewById(R.id.editTextDesc2);

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
        notTitle = i.getStringExtra("title");
        notDescription = i.getStringExtra("description");

        title.setText(notTitle);
        description.setText(notDescription);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateData() {

        String note_title = title.getText().toString();
        String note_description = description.getText().toString();
        Intent data = new Intent();
        data.putExtra("not_title", note_title);
        data.putExtra("note_description", note_description);
        if(id != -1)
        {
            data.putExtra("id", id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
