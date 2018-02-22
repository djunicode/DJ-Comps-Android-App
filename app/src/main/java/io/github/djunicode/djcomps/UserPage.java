package io.github.djunicode.djcomps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserPage extends AppCompatActivity {

    TextView nameview,sapview,bioview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);


        String name="",bio="";

        Long sap=new Long(0);

        nameview=(TextView) findViewById(R.id.name);
        sapview=(TextView) findViewById(R.id.sap);
        bioview=(TextView) findViewById(R.id.bio);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            sap = extras.getLong("sap");
            bio = extras.getString("bio");
        }

        nameview.setText(name);
        sapview.setText(""+sap);
        bioview.setText(""+bio);


    }
}
