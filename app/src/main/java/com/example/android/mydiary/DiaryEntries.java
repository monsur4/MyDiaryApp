package com.example.android.mydiary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.mydiary.database.AppDatabase;
import com.example.android.mydiary.database.DiaryEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DiaryEntries extends AppCompatActivity implements DiaryAdapter.ItemClickListener {

    FloatingActionButton fab;
    AppDatabase mAD;
    RecyclerView mRecyclerView;
    DiaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entries);

        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryEntries.this, AddDiaryActivity.class);
                startActivity(intent);
            }
        });

        mAD = AppDatabase.getsInstance(getApplicationContext());

        //initialize the recyclerview and use a linear layout as the recycler views layout manager
        mRecyclerView = findViewById(R.id.recyclerviewEntries);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize the adapter and attach it to the recyclerview
        adapter = new DiaryAdapter(this, this);
        mRecyclerView.setAdapter(adapter);

        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(divider);

        //sets the lists in the adapter to the diary entries in the database
        retrieveDiaryFromViewModel();
    }

    private void retrieveDiaryFromViewModel(){
        MyViewModel viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        viewModel.getDiaryEntries().observe(this, new Observer<List<DiaryEntry>>() {
            @Override
            public void onChanged(@Nullable List<DiaryEntry> diaryEntries) {
                adapter.setDiaryEntries(diaryEntries);
            }
        });
    }

    /**
     * This listener launches the AddDiaryActivity when a previous entry into the diary is selected
     */

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(DiaryEntries.this, AddDiaryActivity.class);
        intent.putExtra(AddDiaryActivity.EXTRA_DIARY_ENTRY_ID, itemId);
        startActivity(intent);
    }

    /**
     * add the sign_out button as an option Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sign_out){
            //make a call to firebase sign out if the sign out options was clicked
            FirebaseAuth.getInstance().signOut();
        }

        return super.onOptionsItemSelected(item);
    }
}
