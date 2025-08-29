package com.example.blooddonor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DonorsDatabase database;
    private EditText name,phoneNumber,personAge,password,confirmPassword;
    private Spinner bloodGroup,city;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = new DonorsDatabase(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            message();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        name = (EditText) findViewById(R.id.nameID);
        phoneNumber = (EditText) findViewById(R.id.phonenumberID);
        personAge = (EditText) findViewById(R.id.ageID);
        password = (EditText) findViewById(R.id.loginpasswordID);
        confirmPassword = (EditText) findViewById(R.id.confirmpasswordID);
        bloodGroup = (Spinner) findViewById(R.id.bgID);
        city = (Spinner) findViewById(R.id.cityID);
        register = (Button) findViewById(R.id.registerID);

        ArrayAdapter<CharSequence> bgAdapter = ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(bgAdapter);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,R.array.cities,android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityAdapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    }

    public void register()
    {
        String _name = name.getText().toString().trim();
        String _phoneno = phoneNumber.getText().toString().trim();
        String _age = personAge.getText().toString();
        String _bg = bloodGroup.getSelectedItem().toString().trim();
        String _city = city.getSelectedItem().toString().trim();
        String _password = password.getText().toString().trim();
        String _cpassword = confirmPassword.getText().toString().trim();

        if( ! (_name.isEmpty() ||_phoneno.isEmpty() || _age.isEmpty() || _password.isEmpty() || _cpassword.isEmpty()))
        {

            if(_cpassword.equals(_password))
            {
                try{
                    database.addDonors(_name,_phoneno,_age,_bg,_city,_cpassword);
                    Toast.makeText(RegisterActivity.this,"Registration Successfull, Please Login.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                } catch (Exception e){e.printStackTrace();}

            }
            else
                {
                    Toast.makeText(RegisterActivity.this,"password doesn't match",Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(RegisterActivity.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
    }



    public void message()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
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
