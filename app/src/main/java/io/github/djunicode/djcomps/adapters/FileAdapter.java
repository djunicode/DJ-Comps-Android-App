package io.github.djunicode.djcomps.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.File;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<File> fileList;

    public FileAdapter(){
        fileList = new ArrayList<>();
    }

    public void addFile(File file){
        fileList.add(file);
        notifyItemInserted(fileList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = fileList.get(position);
        holder.title.setText(file.name);

        if(file.no_of_stars > 0){
            holder.noOfStars.setVisibility(View.VISIBLE);
            holder.starIcon.setVisibility(View.VISIBLE);
            holder.noOfStars.setText(String.valueOf(file.no_of_stars));
        }
        else{
            holder.noOfStars.setVisibility(View.GONE);
            holder.starIcon.setVisibility(View.GONE);
        }

        if(file.no_of_downloads > 0){
            holder.noOfDownloads.setVisibility(View.VISIBLE);
            holder.downloadIcon.setVisibility(View.VISIBLE);
            holder.noOfDownloads.setText(String.valueOf(file.no_of_downloads));
        }
        else{
            holder.noOfDownloads.setVisibility(View.GONE);
            holder.downloadIcon.setVisibility(View.GONE);
        }


        if(file.isDownloaded){
            holder.isDownloadedIcon.setVisibility(View.VISIBLE);
        }
        else{
            holder.isDownloadedIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, noOfStars, noOfDownloads;
        ImageView isDownloadedIcon, starIcon, downloadIcon;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.file_title);
            noOfStars = itemView.findViewById(R.id.file_no_stars);
            noOfDownloads = itemView.findViewById(R.id.file_no_downloads);
            isDownloadedIcon = itemView.findViewById(R.id.file_download_status);
            starIcon = itemView.findViewById(R.id.file_stars_icon);
            downloadIcon = itemView.findViewById(R.id.file_downloads_icon);

        }

    }
}
