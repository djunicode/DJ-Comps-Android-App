package io.github.djunicode.djcomps.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.djunicode.djcomps.FileDetailActivity;
import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.Utils;
import io.github.djunicode.djcomps.database.data.File;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<File> fileList;
    private Context context;
    private boolean isHorizontal;

    public FileAdapter(Context context, boolean isHorizontal){
        fileList = new ArrayList<>();
        this.context = context;
        this.isHorizontal = isHorizontal;
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
        final File file = fileList.get(position);
        holder.titleTV.setText(file.name);

        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        holder.uploadDateTV.setText(dateFormat.format(file.time_added));

        if(file.no_of_stars > 0){
            holder.noOfStarsTV.setVisibility(View.VISIBLE);
            holder.starIcon.setVisibility(View.VISIBLE);
            holder.noOfStarsTV.setText(String.valueOf(file.no_of_stars));
        }
        else{
            holder.noOfStarsTV.setVisibility(View.GONE);
            holder.starIcon.setVisibility(View.GONE);
        }

        if(file.no_of_downloads > 0){
            holder.noOfDownloadsTV.setVisibility(View.VISIBLE);
            holder.downloadIcon.setVisibility(View.VISIBLE);
            holder.noOfDownloadsTV.setText(String.valueOf(file.no_of_downloads));
        }
        else{
            holder.noOfDownloadsTV.setVisibility(View.GONE);
            holder.downloadIcon.setVisibility(View.GONE);
        }


        if(file.is_downloaded){
            holder.isDownloadedIcon.setVisibility(View.VISIBLE);
        }
        else{
            holder.isDownloadedIcon.setVisibility(View.GONE);
        }

        holder.fileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FileDetailActivity.class);
                intent.putExtra(FileDetailActivity.FILE_INFO_PARCEL, file);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView fileCard;
        TextView titleTV, uploadDateTV, noOfStarsTV, noOfDownloadsTV;
        ImageView isDownloadedIcon, starIcon, downloadIcon;

        ViewHolder(View itemView) {
            super(itemView);
            fileCard = itemView.findViewById(R.id.file_card);
            titleTV = itemView.findViewById(R.id.file_title);
            uploadDateTV = itemView.findViewById(R.id.file_uploaded_date);
            noOfStarsTV = itemView.findViewById(R.id.file_no_stars);
            noOfDownloadsTV = itemView.findViewById(R.id.file_no_downloads);
            isDownloadedIcon = itemView.findViewById(R.id.file_download_status);
            starIcon = itemView.findViewById(R.id.file_stars_icon);
            downloadIcon = itemView.findViewById(R.id.file_downloads_icon);

            if(isHorizontal){
                ViewGroup.LayoutParams params = fileCard.getLayoutParams();
                params.width = Utils.dpToPx(180);
                fileCard.setLayoutParams(params);
            }

        }

    }
}
