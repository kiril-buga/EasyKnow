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

public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.MyViewHolder>{
    private ArrayList<LearnFolder> foldersList;
    private RecyclerViewClickListener listener;


    public FoldersAdapter(ArrayList<LearnFolder> foldersList, RecyclerViewClickListener listener) {
        this.foldersList = foldersList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView folderName;

        public MyViewHolder(final View view) {
            super(view);
            folderName = view.findViewById(R.id.FolderItem);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public FoldersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_folders, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoldersAdapter.MyViewHolder holder, int position) {
        String folder = foldersList.get(position).getFolderTitle();
        holder.folderName.setText(folder);
    }

    @Override
    public int getItemCount() {
        return foldersList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
