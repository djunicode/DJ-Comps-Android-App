package io.github.djunicode.djcomps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FileDetails extends AppCompatActivity {

    TextView filename,sub,create;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        String name="",creator="",subject="";
        //int img=R.drawable.img1;
        filename=(TextView) findViewById(R.id.filename);
        sub=(TextView)findViewById(R.id.subject);
        create=(TextView)findViewById(R.id.creator);
        //image=(ImageView)findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("filename");
            creator = extras.getString("creator");
            //img =Integer.parseInt(extras.getString("image"));
            subject = extras.getString("subject");

            //The key argument here must match that used in the other activity
        }

        filename.setText(name);
        sub.setText(subject);
        create.setText(creator);
       // image.setImageResource(img);

    }
}
