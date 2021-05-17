package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import EasyKnowLib.LearnFolder;
import EasyKnowLib.LearnItem;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder> {
    private ArrayList<LearnItem> wordsList;
    private WordsAdapter.RecyclerViewClickListener listener;


    public WordsAdapter(ArrayList<LearnItem> wordsList, RecyclerViewClickListener listener) {
        this.wordsList = wordsList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView wordName;
        private TextView wordMeaning;

        public MyViewHolder(final View view) {
            super(view);
            wordName = view.findViewById(R.id.wordTitle);
            wordMeaning = view.findViewById(R.id.wordMeaning);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public WordsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_words, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsAdapter.MyViewHolder holder, int position) {
        String word = wordsList.get(position).getTitle();
        String meaning = wordsList.get(position).getMeaning();
        holder.wordName.setText(word);
        holder.wordMeaning.setText(meaning);
    }

    @Override
    public int getItemCount() {
        return wordsList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
