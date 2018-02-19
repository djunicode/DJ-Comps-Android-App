package io.github.djunicode.djcomps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Admin on 29-12-2017.
 */

public class DeadlineAdapter extends RecyclerView.Adapter<DeadlineAdapter.ViewHolder> {
    private Context mContext;
    private List<Deadline> deadlinelist;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title, subject, date;
        public ImageView img;
        private CardView cardView;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.dCard);
            title = (TextView) view.findViewById(R.id.name);
            subject = (TextView) view.findViewById(R.id.star);
            date = (TextView) view.findViewById(R.id.date);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeadlineAdapter(Context mContext, List<Deadline> deadlinelist) {
        this.mContext = mContext;
        this.deadlinelist=deadlinelist;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DeadlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deadline_card, parent, false);



        return new DeadlineAdapter.ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DeadlineAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Deadline album = deadlinelist.get(position);
        holder.title.setText(album.getName());
        holder.subject.setText(album.getSubject());
        holder.date.setText(album.getDate());


        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImg()).into(holder.img);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return deadlinelist.size();
    }

}
