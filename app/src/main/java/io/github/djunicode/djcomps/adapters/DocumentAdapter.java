package io.github.djunicode.djcomps.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.File;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private List<File> documentList;

    public DocumentAdapter(){
        documentList = new ArrayList<>();
    }

    public void addDocument(File document){
        documentList.add(document);
        notifyItemInserted(documentList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.documents_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File doc = documentList.get(position);
        holder.title.setText(doc.name);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

    }
}
