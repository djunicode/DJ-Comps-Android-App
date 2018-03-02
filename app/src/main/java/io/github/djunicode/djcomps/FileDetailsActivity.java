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

public class FileDetailsActivity extends AppCompatActivity {

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

        CollapsingToolbarLayout ctl = findViewById(R.id.file_detail_collapse_toolbar);
        ctl.setTitle(getString(R.string.placeholder_text));

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
