package com.example.android.mydiary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.mydiary.database.AppDatabase;
import com.example.android.mydiary.database.DiaryEntry;

import java.util.Date;

public class AddDiaryActivity extends AppCompatActivity {

    // Diary entry id stored as a constant value and to be used when updating a previous entry
    public static final String EXTRA_DIARY_ENTRY_ID = "extraDiaryEntryId";

    // default id stored as a constant value and to be used when not updating a previous entry
    private static final int DEFAULT_DIARY_ENTRY_ID = -1;

    private static int mId = DEFAULT_DIARY_ENTRY_ID;

    EditText mTitle;
    EditText mContent;
    Button mButtonSave;

    AppDatabase mAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        initializeViews();

        mAD = AppDatabase.getsInstance(getApplicationContext());

        // get the intent that launches this AddDiaryActivity, and obtain the extra id if available
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_DIARY_ENTRY_ID)){
            if(mId == DEFAULT_DIARY_ENTRY_ID){
                Intent parentIntent = getIntent();
                mId = parentIntent.getIntExtra(EXTRA_DIARY_ENTRY_ID, DEFAULT_DIARY_ENTRY_ID);
                AddDiaryViewModelFactory factory = new AddDiaryViewModelFactory(mAD, mId);
                final AddDiaryEntryViewModel viewModel = ViewModelProviders.of(this, factory).get(AddDiaryEntryViewModel.class);
                viewModel.getDiaryEntry().observe(this, new Observer<DiaryEntry>() {
                    @Override
                    public void onChanged(@Nullable DiaryEntry diaryEntry) {
                        viewModel.getDiaryEntry().removeObserver(this);
                        populateUI(diaryEntry);
                    }
                });
            }
        }
    }

    private void populateUI(DiaryEntry diaryEntry) {
        if (diaryEntry != null){
            mTitle.setText(diaryEntry.getTitle());
            mContent.setText(diaryEntry.getDetails());
        }
    }

    /**
     * This method initializes the views, and it is called within onCreate
     */
    private void initializeViews() {
        mTitle = findViewById(R.id.editText_title);
        mContent = findViewById(R.id.editText_content);
        mButtonSave = findViewById(R.id.button_save);

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }


    private void onSaveButtonClicked() {
        String title = mTitle.getText().toString();
        String details = mContent.getText().toString();
        Date date = new Date();

        DiaryEntry diaryEntry = new DiaryEntry(title, details, date);
        if (mId == DEFAULT_DIARY_ENTRY_ID) {
            mAD.diaryDao().insertDiary(diaryEntry);
        }
        else {
            diaryEntry.setId(mId);
            mAD.diaryDao().updateDiary(diaryEntry);
            //sets the Id back to the default
            mId = DEFAULT_DIARY_ENTRY_ID;
        }
            finish();
    }
}
