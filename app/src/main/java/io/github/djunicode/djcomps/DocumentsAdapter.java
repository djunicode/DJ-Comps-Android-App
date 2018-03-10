package io.github.djunicode.djcomps;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.*;

import io.github.djunicode.djcomps.Database.Data.File;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {
    private Context mContext;
    private List<File> documents;
    private static OnItemClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView title, subject, access;
        public ImageView img;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //subject = (TextView) view.findViewById(R.id.subject);
            //access = (TextView) view.findViewById(R.id.access);
            img = (ImageView) view.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DocumentsAdapter(Context mContext, List<File> documents) {
        this.mContext = mContext;
        this.documents=documents;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.documents_card, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        File album = documents.get(position);
        holder.title.setText(album.name);
        //holder.subject.setText(album.getSubject());
        //holder.access.setText(album.getAccess());


        // loading album cover using Glide library
       // Glide.with(mContext).load(album.img).into(holder.img);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return documents.size();
    }

}
