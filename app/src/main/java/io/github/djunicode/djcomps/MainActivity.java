package io.github.djunicode.djcomps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText sapID = findViewById(R.id.sap_id);
    EditText password = findViewById(R.id.password);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void login(View view) {
        Intent myIntent = new Intent(view.getContext(), Home.class);
        startActivityForResult(myIntent, 0);
        HTTPRequests.onLoginRequest(sapID.getText().toString(), password.getText().toString());
    }

}




