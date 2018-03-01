package io.github.djunicode.djcomps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import io.github.djunicode.djcomps.fragments.HomeFragment;


public class LoginActivity extends AppCompatActivity {

    // Login Preferences
    public static final String SP_LOGIN_ID = "LoginPreferences";
    public static final String SP_LOGIN_LOGGED_IN_STATE = "LoggedInState";
    public static final String SP_LOGIN_USER_SAP = "UserSAP";
    public static final String SP_LOGIN_USER_TOKEN = "UserToken";

    // Lock to avoid deadlock
    private static boolean lockLogin = false;

    // UI references.
    private EditText sapET;
    private EditText passwordView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        sapET = findViewById(R.id.login_username);

        passwordView = findViewById(R.id.login_password);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 99 || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button emailSignInButton = findViewById(R.id.login_sign_in);
        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        setupProgressDialog();
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
    }

    private void attemptLogin() {
        if (lockLogin) {
            return;
        }

        // Reset errors.
        sapET.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = sapET.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isValidPassword(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            sapET.setError(getString(R.string.error_field_required));
            focusView = sapET;
            cancel = true;
        } else if (!isValidSAP(email)) {
            sapET.setError(getString(R.string.error_invalid_sap));
            focusView = sapET;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            progressDialog.setMessage(getString(R.string.login_authenticating));
            progressDialog.show();
            signIn(email, password);
        }
    }

    static boolean isValidSAP(String sap) {
        return sap.matches("[-+]?\\d*\\.?\\d+");
    }

    static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    void signIn(final String sap, final String password) {
        new signInTask(this, sap, password, sapET, passwordView, progressDialog).execute();
    }

    private static class signInTask extends AsyncTask<Void, Void, String> {

        WeakReference<Context> wrContext;
        WeakReference<Activity> wrActivity;
        WeakReference<EditText> wrSapET, wrPassET;
        String sap, password;
        ProgressDialog progressDialog;

        signInTask(Activity activity, final String sap, final String password,
                   EditText sapET, EditText passET, ProgressDialog progressDialog){
            this.wrActivity = new WeakReference<>(activity);
            this.wrContext = new WeakReference<>(activity.getApplicationContext());
            this.wrSapET = new WeakReference<>(sapET);
            this.wrPassET = new WeakReference<>(passET);
            this.sap = sap;
            this.password = password;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute(){
            lockLogin = true;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: do verifications from server
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String token){
            if(token != null){
                SharedPreferences prefs = wrContext.get().getSharedPreferences(SP_LOGIN_ID, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putBoolean(SP_LOGIN_LOGGED_IN_STATE, true);
                editor.putString(SP_LOGIN_USER_SAP, sap);
                editor.putString(SP_LOGIN_USER_TOKEN, token);

                editor.apply();

                wrActivity.get().finish();
                wrActivity.get().startActivity(new Intent(wrContext.get(), HomeFragment.class));
            }
            else{
                wrSapET.get().setError(wrContext.get().getString(R.string.error_incorrect_password));
                wrPassET.get().setError(wrContext.get().getString(R.string.error_incorrect_password));
                wrPassET.get().requestFocus();
            }
            lockLogin = false;
            progressDialog.dismiss();
        }
    }
}




