package com.example.android.mydiary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mydiary.database.DiaryEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    private Context mContext;
    private List<DiaryEntry> mDiaryEntries;

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    // To format the date
    private SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");


    public DiaryAdapter(Context mContext, ItemClickListener mItemClickListener) {
        this.mContext = mContext;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DiaryEntry diaryEntry = mDiaryEntries.get(position);
        String title = diaryEntry.getTitle();
        Date date = diaryEntry.getDateModified();
        String dayModified = dayFormat.format(date);
        String monthModified = monthFormat.format(date);
        String yearModified = yearFormat.format(date);
        String dateUpdated = "" + dayModified + "/" + monthModified + "/" + yearModified;

        holder.title.setText(title);
        holder.date.setText(dateUpdated);
    }

    @Override
    public int getItemCount() {
        if (mDiaryEntries == null) {
            return 0;
        }
        return mDiaryEntries.size();
    }

    public List<DiaryEntry> getDiaryEntries(){
        return mDiaryEntries;
    }

    public void setDiaryEntries(List<DiaryEntry> diaryEntry){
        mDiaryEntries = diaryEntry;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView date;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemId = mDiaryEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(itemId);
        }
    }
}


