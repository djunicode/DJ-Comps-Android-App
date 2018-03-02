package io.github.djunicode.djcomps;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.github.djunicode.djcomps.database.data.File;

public class FileDetailsActivity extends AppCompatActivity {

    public static final String FILE_INFO_PARCEL = "file_parcel";

    private Menu mMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_details);

        Toolbar toolbar = findViewById(R.id.file_detail_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.file_detail_appbar_layout);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle parcelBundle = getIntent().getExtras();
        if (parcelBundle != null) {
            File file = parcelBundle.getParcelable(FILE_INFO_PARCEL);
            setFileDetails(file);
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset != 0){
                    appBarLayout.findViewById(R.id.file_detail_date_holder).setVisibility(View.INVISIBLE);
                }
                else{
                    appBarLayout.findViewById(R.id.file_detail_date_holder).setVisibility(View.VISIBLE);
                }

                if(mMenu != null){
                    if(Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
                        mMenu.findItem(R.id.file_detail_menu_action).setVisible(true);
                    }
                    else{
                        mMenu.findItem(R.id.file_detail_menu_action).setVisible(false);
                    }
                }
            }
        });

    }

    private void setFileDetails(File file){
        CollapsingToolbarLayout ctl = findViewById(R.id.file_detail_collapse_toolbar);
        ctl.setTitle(file.name);

        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        TextView dateHeaderTV = findViewById(R.id.file_detail_date_header);
        TextView dateTV = findViewById(R.id.file_detail_date);
        TextView nameTV = findViewById(R.id.file_detail_name);
        TextView typeTV = findViewById(R.id.file_detail_type);
        TextView sizeTV = findViewById(R.id.file_detail_size);
        TextView addedByTV = findViewById(R.id.file_detail_added_by);
        TextView noStarsTV = findViewById(R.id.file_detail_no_stars);
        TextView noDownloadsTV = findViewById(R.id.file_detail_no_downloads);
        TextView descriptionTV = findViewById(R.id.file_detail_description);

        dateHeaderTV.setText(dateFormat.format(file.time_added));
        dateTV.setText(dateFormat.format(file.time_added));
        nameTV.setText(file.name);
        typeTV.setText(file.type);

        //TODO: long to string of file size conversion needed
        sizeTV.setText(String.valueOf(file.size));
        //TODO: get user instead of sap id
        addedByTV.setText(String.valueOf(file.sap_id));

        noStarsTV.setText(String.valueOf(file.no_of_stars));
        noDownloadsTV.setText(String.valueOf(file.no_of_downloads));
        descriptionTV.setText(file.description);

        //TODO: shared with section

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
