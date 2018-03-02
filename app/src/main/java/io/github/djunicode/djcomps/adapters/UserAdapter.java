package io.github.djunicode.djcomps.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context){
        userList = new ArrayList<>();
        this.context = context;
    }

    public void addUser(User user){
        userList.add(user);
        notifyItemInserted(userList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);

        holder.usernameTV.setText(user.name);

        //TODO: Replace integer with string with corresponding group string
        holder.usertypeTV.setText(String.valueOf(user.group_id));

        //TODO: fetch image from url
        Picasso.with(context).load(R.drawable.ic_person_black_24dp).into(holder.profileImageIV);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTV, usertypeTV;
        ImageView profileImageIV;

        ViewHolder(View itemView) {
            super(itemView);
            usernameTV = itemView.findViewById(R.id.card_user_name);
            usertypeTV = itemView.findViewById(R.id.card_user_type);
            profileImageIV = itemView.findViewById(R.id.card_user_img);
        }

    }
}
