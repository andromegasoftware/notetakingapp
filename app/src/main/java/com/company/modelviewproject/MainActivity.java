package com.company.modelviewproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int add_note = 1;
    public static final int edit_note = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Super Note"); // set the top title


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update Recyclerview here
                adapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent i = new Intent(MainActivity.this, editnote.class);
                i.putExtra("title", note.getTitle());
                i.putExtra("description", note.getDescription());
                i.putExtra("id", note.getId());
                startActivityForResult(i, edit_note);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent intent = new Intent(MainActivity.this, addnote.class);
                startActivityForResult(intent, add_note);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == add_note && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra("not_title");
            String description = data.getStringExtra("note_description");

            Note note = new Note(title, description);
            noteViewModel.insert(note);
        }
        else if(requestCode == edit_note && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("id", -1);
            if(id == -1)
            {
                Toast.makeText(getApplicationContext(), "Note cannot be updated", Toast.LENGTH_LONG).show();
            }
            String title = data.getStringExtra("not_title");
            String description = data.getStringExtra("note_description");

            Note note = new Note(title, description);
            note.setId(id);
            noteViewModel.update(note);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Nothing Saved", Toast.LENGTH_LONG).show();

        }
    }
}
