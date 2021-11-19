package com.example.waterkersandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edGebruikersnaam, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        edGebruikersnaam = findViewById(R.id.edGebruikersnaam);
        edPassword = findViewById(R.id.edPassword);

        Login();
    }
    public void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Overzicht.class);
                startActivity(intent);
            }
        });
    }
}