package com.example.blooddonor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private Spinner city,bloodGroup;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            message();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        city = (Spinner) findViewById(R.id.searchCityID);
        bloodGroup = (Spinner) findViewById(R.id.searchBgID);
        searchButton = (Button) findViewById( R.id.searchButtonID);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);

        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(bloodGroupAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,DonorsList.class);
                intent.putExtra("city",city.getSelectedItem().toString());
                intent.putExtra("bg",bloodGroup.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }

    public void message()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SearchActivity.this);
        dialog.setTitle("Notice");
        dialog.setMessage("As of now this application supports in Karnataka state only!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create();
        dialog.show();
    }
}
