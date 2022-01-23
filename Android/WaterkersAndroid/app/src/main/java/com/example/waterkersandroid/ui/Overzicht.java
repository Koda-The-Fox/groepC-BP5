package com.example.waterkersandroid.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waterkersandroid.R;

public class Overzicht extends AppCompatActivity {

    TextView TVKas, TVpH, TVLT, TVLV, TVGV, TVGT;
    ImageView iv_pH, iv_LuchtTemp, iv_LuchtVocht, iv_GrondTemp, iv_GrondVocht;
    String pH, luchttemp, luchtvocht, grondtemp, grondvocht;
    Context ctxx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

        //TextView
        TVKas = findViewById(R.id.TV_kasnummer);
        TVpH = findViewById(R.id.TV_pHwaarde);
        TVLT = findViewById(R.id.TV_luchtTemp_waarde);
        TVLV = findViewById(R.id.TV_luchtVocht_waarde);
        TVGV = findViewById(R.id.TV_bodemTemp_waarde);
        TVGT = findViewById(R.id.TV_bodemVocht_waarde);

        //ImageView
        iv_pH = findViewById(R.id.IV_pH);
        iv_LuchtTemp = findViewById(R.id.IV_LT);
        iv_LuchtVocht = findViewById(R.id.IV_LV);
        iv_GrondTemp = findViewById(R.id.IV_GT);
        iv_GrondVocht = findViewById(R.id.IV_GV);

//        List<String> list= new ArrayList<String>();
//        SampleSQLiteDBHelper DB = new SampleSQLiteDBHelper(ctxx);
//        DB.open();
//        Cursor CR=DB.getInformation(DB);
//        if (CR.moveToFirst()) {
//            do {
//                list.add(CR.getString(0));
//                pH = CR.getString(0);
//                luchttemp = CR.getString(1);
//                luchtvocht = CR.getString(2);
//                grondtemp = CR.getString(3);
//                grondvocht = CR.getString(4);
//            } while (CR.moveToNext());
//        }
//        if (CR != null && !CR.isClosed()){
//            CR.close();
//        }
//        TVpH.setText(pH);


        pH_waarde();
        LT_waarde();
        LV_waarde();
        GT_waarde();
        GV_waarde();
    }

    public void pH_waarde() {

        String pH = TVpH.getText().toString();

        if (pH.equals("7"))
            iv_pH.setVisibility(View.INVISIBLE);
        else {
            if (pH.equals("8"))
                iv_pH.setVisibility(View.VISIBLE);
        }
    }
    public void LT_waarde() {

        String luchttemp = TVLT.getText().toString();

        if (luchttemp.equals("17℃"))
            iv_LuchtTemp.setVisibility(View.INVISIBLE);
        else {
            if (luchttemp.equals("18℃"))
                iv_LuchtTemp.setVisibility(View.VISIBLE);
        }
    }
    public void LV_waarde(){
        String luchtvocht = TVLV.getText().toString();

        if (luchtvocht.equals("70%"))
            iv_LuchtVocht.setVisibility(View.INVISIBLE);
        else {
            if (luchtvocht.equals("60%"))
                iv_LuchtVocht.setVisibility(View.VISIBLE);
        }
    }
    public void GT_waarde(){
        String grondTemp = TVGT.getText().toString();

        if (grondTemp.equals("13℃"))
            iv_GrondTemp.setVisibility(View.INVISIBLE);
        else {
            if (grondTemp.equals("10℃"))
                iv_GrondTemp.setVisibility(View.VISIBLE);
        }
    }
    public void GV_waarde(){
        String grondVocht = TVGV.getText().toString();

        if (grondVocht.equals("70%"))
            iv_GrondVocht.setVisibility(View.INVISIBLE);
        else {
            if (grondVocht.equals("60%"))
                iv_GrondVocht.setVisibility(View.VISIBLE);
        }
    }
}