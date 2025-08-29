package com.example.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button login,register;
    private EditText getPhonenumber;
    private EditText getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final DonorsDatabase database = new DonorsDatabase(this);
        getPhonenumber = (EditText) findViewById(R.id.loginphoneNumberID);
        getPassword = (EditText) findViewById(R.id.loginpasswordID);
        login = (Button) findViewById(R.id.loginID);
        register = (Button) findViewById(R.id.registerID);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = getPassword.getText().toString().trim();
                String phonenumber = getPhonenumber.getText().toString().trim();


                boolean res = database.getMatched( phonenumber, password);
                    if(res == true)
                    {
                         Toast.makeText(LoginActivity.this,"Login successfull",Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(LoginActivity.this,DonorProfile.class);
                         intent.putExtra("PhNO",phonenumber);
                         intent.putExtra("Password",password);
                         startActivity(intent);
                         finish();
                     }
                    else {
                        Log.d("msg","msg"+res);
                        Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                    }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}
