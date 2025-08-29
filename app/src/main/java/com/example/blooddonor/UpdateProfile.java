package com.example.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProfile extends AppCompatActivity {
    Bundle extras;
    EditText name,phonenumber,age,password;
    Spinner bg,city;
    Button update;
    private String intentName,intentPhoneNumber,intentAge;
    DonorsDatabase database = new DonorsDatabase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = (EditText) findViewById(R.id.updateProfileNameID);
        phonenumber = (EditText) findViewById(R.id.updateProfilePhoneNumberID);
        age = (EditText) findViewById(R.id.updateProfileAgeID);
        bg = (Spinner) findViewById(R.id.updateBgID);
        city = (Spinner) findViewById(R.id.updateCityID);
        update = (Button) findViewById(R.id.updateProfileButtonID);
        password = (EditText) findViewById(R.id.updatePasswordID);

        extras = getIntent().getExtras();
        if (extras != null)
        {
            intentName = extras.getString("Name");
            intentPhoneNumber = extras.getString("PhNo");
            intentAge = extras.getString("Age");
        }

        ArrayAdapter<CharSequence> bgAdapter = ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bg.setAdapter(bgAdapter);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);

        name.setText(intentName);
        phonenumber.setText(intentPhoneNumber);
        age.setText(intentAge);
        password.setText(database.getData(intentPhoneNumber).get(5));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

               String uName = name.getText().toString();
               String uPhoneNumber = phonenumber.getText().toString();
               String uAge = age.getText().toString();
               String uBg = bg.getSelectedItem().toString().trim();
               String uCity = city.getSelectedItem().toString().trim();
               String uPassword = password.getText().toString();

                if ( database.update(intentPhoneNumber,uPhoneNumber,uName,uAge,uBg,uCity,uPassword)==true)
                {
                    Toast.makeText(UpdateProfile.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }
}
