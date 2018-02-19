package io.github.djunicode.djcomps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class FileDetails extends AppCompatActivity {

    TextView filename,idview,sapview,timeview,sizeview,starsview,downloadsview,typeview,descriptionview;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        String name="",type="",description="";

        Long id=new Long(0);
        Long sap=new Long(0);
        Long size=new Long(0);
        Long time=new Long(0);
        //Date time=new Date();

        int stars=0,downloads=0;

        //int img=R.drawable.img1;
        filename=(TextView) findViewById(R.id.filename);
        idview=(TextView)findViewById(R.id.id);
        sapview=(TextView)findViewById(R.id.sap);
        timeview=(TextView)findViewById(R.id.time);
        sizeview=(TextView)findViewById(R.id.size);
        starsview=(TextView)findViewById(R.id.star);
        downloadsview=(TextView)findViewById(R.id.downloads);
        typeview=(TextView)findViewById(R.id.type);
        descriptionview=(TextView)findViewById(R.id.description);
        //image=(ImageView)findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("filename");
            id = extras.getLong("id");
            //img =Integer.parseInt(extras.getString("image"));
            sap = extras.getLong("sap");
            time = extras.getLong("time",0);
            size = extras.getLong("size");
            stars = extras.getInt("stars");
            downloads = extras.getInt("downloads");
            type = extras.getString("type");
            description = extras.getString("description");


            //The key argument here must match that used in the other activity

            /*
            * i.putExtra("filename", doc.name);
        i.putExtra("id", doc.file_id);
        i.putExtra("sap",doc.sap_id);
        i.putExtra("time",doc.time_added);
        i.putExtra("size",doc.size);
        i.putExtra("stars",doc.no_of_stars);
        i.putExtra("downloads",doc.no_of_downloads);
        i.putExtra("type",doc.type);
        i.putExtra("description",doc.description);*/
        }

        filename.setText(name);
        idview.setText(""+id);
        sapview.setText(""+sap);
        timeview.setText(""+time);
        sizeview.setText(""+size);
        starsview.setText(""+stars);
        downloadsview.setText(""+downloads);
        typeview.setText(type);
        descriptionview.setText(description);
       // image.setImageResource(img);

    }
}
