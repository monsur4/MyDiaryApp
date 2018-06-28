package com.example.android.mydiary;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.mydiary.database.AppDatabase;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */

public class AddDiaryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int mId;
    private final AppDatabase mDB;

    public AddDiaryViewModelFactory(AppDatabase dB, int id) {
        mDB = dB;
        mId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new AddDiaryEntryViewModel(mDB, mId);
    }
}
