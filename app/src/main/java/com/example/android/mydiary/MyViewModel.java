package com.example.android.mydiary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.mydiary.database.AppDatabase;
import com.example.android.mydiary.database.DiaryEntry;

import java.util.List;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */

public class MyViewModel extends AndroidViewModel {

    private LiveData<List<DiaryEntry>> diaryEntries;
    public MyViewModel(@NonNull Application application) {
        super(application);
        AppDatabase dB = AppDatabase.getsInstance(this.getApplication());
        diaryEntries = dB.diaryDao().loadAllDiary();
    }

    public LiveData<List<DiaryEntry>> getDiaryEntries(){
        return diaryEntries;
    }
}
