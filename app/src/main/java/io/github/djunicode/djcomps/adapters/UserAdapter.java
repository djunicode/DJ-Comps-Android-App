package io.github.djunicode.djcomps.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.User;
import io.github.djunicode.djcomps.dialogs.UserDetailDialog;
import io.github.djunicode.djcomps.fragments.FileViewFragment;

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
        final User user = userList.get(position);

        holder.userNameTV.setText(user.name);

        //TODO: Replace integer with string with corresponding group string
        holder.userTypeTV.setText(String.valueOf(user.group_id));

        //TODO: fetch image from url

        holder.infoButtonRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetailDialog dialog = new UserDetailDialog(context, user);
                dialog.show();
            }
        });

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                Fragment fragment = FileViewFragment.getInstance(FileViewFragment.Type.Explore);
                transaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView userCard;
        TextView userNameTV, userTypeTV;
        ImageView profileImageIV;
        View infoButtonRV;

        ViewHolder(View itemView) {
            super(itemView);
            userCard = itemView.findViewById(R.id.user_card);
            userNameTV = itemView.findViewById(R.id.card_user_name);
            userTypeTV = itemView.findViewById(R.id.card_user_type);
            profileImageIV = itemView.findViewById(R.id.card_user_img);
            infoButtonRV = itemView.findViewById(R.id.user_info_rv_button);
        }

    }
}
