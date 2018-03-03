package io.github.djunicode.djcomps.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import io.github.djunicode.djcomps.R;
import io.github.djunicode.djcomps.database.data.User;

public class UserDetailDialog extends Dialog {

    private User user;

    public UserDetailDialog(@NonNull Context context, User user) {
        super(context);
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_user_details);

        TextView usernameTV = findViewById(R.id.user_detail_user_name);
        TextView userTypeTV = findViewById(R.id.user_detail_user_type);
        View studentInfoView = findViewById(R.id.user_detail_student_detail);
        TextView studentSapTV = findViewById(R.id.user_detail_student_sap);
        TextView studentClassTV = findViewById(R.id.user_detail_student_class);
        TextView userBioTV = findViewById(R.id.user_detail_user_bio);

        usernameTV.setText(this.user.name);
        userBioTV.setText(this.user.bio);

        //TODO: check group of user to check if teacher or student; for now student
        boolean isStudent = true;

        if(isStudent){
            userTypeTV.setText("Student");
            studentInfoView.setVisibility(View.VISIBLE);
            studentSapTV.setText(String.valueOf(this.user.sap_id));
            //TODO: get class from group id
            studentClassTV.setText(String.valueOf(this.user.group_id));
        }
        else{
            userTypeTV.setText("Teacher");
            studentInfoView.setVisibility(View.GONE);
        }

        //TODO: load image from url into imageview

    }
}
