package com.example.waterkersandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.waterkersandroid.R;
import com.example.waterkersandroid.database.SQLite.SampleSQLiteDBHelper;
import com.example.waterkersandroid.model.SampleSQLite.Gebruiker;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText loginNaam, loginPass;
    private SampleSQLiteDBHelper waterkersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gebruiker sampleGebruiker = new Gebruiker();
        sampleGebruiker.LoginNaam="charlotte";
        sampleGebruiker.LoginPass="charlotte1";

        btnLogin = findViewById(R.id.btnLogin);
        loginNaam = findViewById(R.id.edGebruikersnaam);
        loginPass = findViewById(R.id.edPassword);


//        LoginTest();

        Login();
    }
    public void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LoginNaam = loginNaam.getText().toString();
                String LoginPass = loginPass.getText().toString();

                if (loginNaam.equals("")||loginPass.equals(""))
                    Toast.makeText(MainActivity.this, "Vul alsjeblieft alle velden in", Toast.LENGTH_SHORT).show();
                else {
                    if (loginNaam.equals("charlotte") || loginPass.equals("charlotte1"))
                        Toast.makeText(MainActivity.this, "inlog succesvol", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), Overzicht.class);
                    startActivity(intent);
                }
            }
        });
    }
//    public void LoginTest(){
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), Overzicht.class);
//                startActivity(intent);
//            }
//        });
//    }
//    public void Login(){
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View l) {
//
//                String user = edGebruikersnaam.getText().toString();
//                String pass = edPassword.getText().toString();
//
//                if (user.equals("")||pass.equals(""))
//                    Toast.makeText(MainActivity.this, "Vul alsjeblieft alle velden in", Toast.LENGTH_SHORT).show();
//                else{
//                    Boolean checkuserpass = waterkersDB.checkusernamepassword(user, pass);
//                    if (checkuserpass==true) {
//                        Toast.makeText(MainActivity.this, "Inloggen succesvol", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), Overzicht.class);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(MainActivity.this, "gebruikersnaam of wachtwoord klopt niet", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//    }

}