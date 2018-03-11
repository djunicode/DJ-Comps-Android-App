package io.github.djunicode.djcomps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import io.github.djunicode.djcomps.fragments.FileViewFragment;
import io.github.djunicode.djcomps.fragments.HomeFragment;
import io.github.djunicode.djcomps.fragments.UserViewFragment;

import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_ID;
import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_LOGGED_IN_STATE;
import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_USER_SAP;
import static io.github.djunicode.djcomps.LoginActivity.SP_LOGIN_USER_TOKEN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        new LoginTask(this, progressDialog).execute();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }


    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_upload:
                fragment = FileViewFragment.getInstance(FileViewFragment.Type.Uploads);
                break;
            case R.id.nav_explore:
                fragment = FileViewFragment.getInstance(FileViewFragment.Type.Explore);
                break;
            case R.id.nav_starred:
                fragment = FileViewFragment.getInstance(FileViewFragment.Type.Stars);
                break;
            case R.id.nav_downloaded:
                fragment = FileViewFragment.getInstance(FileViewFragment.Type.Downloads);
                break;
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_logout:
                SharedPreferences prefs = getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);
                prefs.edit().putBoolean(SP_LOGIN_LOGGED_IN_STATE, false).apply();
                this.recreate();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private static class LoginTask extends AsyncTask<Void, Void, Void> {

        WeakReference<Activity> wrActivity;
        WeakReference<Context> wrContext;
        ProgressDialog progressDialog;

        LoginTask(Activity activity, ProgressDialog pd){
            wrActivity = new WeakReference<>(activity);
            wrContext = new WeakReference<>(activity.getApplicationContext());
            this.progressDialog = pd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(wrContext.get().getString(R.string.login_logging_in));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences prefs = wrContext.get().getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean(SP_LOGIN_LOGGED_IN_STATE, false);

            //TODO: check token with server

            if(!isLoggedIn){
                wrActivity.get().finish();
                wrActivity.get().startActivity(new Intent(wrActivity.get(), LoginActivity.class));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPreExecute();
            progressDialog.dismiss();
        }

    }

}
