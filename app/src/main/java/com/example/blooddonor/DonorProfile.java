package com.example.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DonorProfile extends AppCompatActivity {

    private TextView pName,pPhone,pAge,pBg,pCity;
    private Button delete,update;
    Bundle extras;
    private ImageButton reload;
    DonorsDatabase database = new DonorsDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        pName = (TextView) findViewById(R.id.profileNameID);
        pPhone = (TextView) findViewById(R.id.profilePhonenumberID);
        pAge = (TextView) findViewById(R.id.profileAgeID);
        pBg = (TextView) findViewById(R.id.profileBgID);
        pCity = (TextView) findViewById(R.id.profileCityID);
        delete = (Button) findViewById(R.id.deleteAccountID);
        update = (Button) findViewById(R.id.updateProfileButtonID);
        reload = (ImageButton) findViewById(R.id.reloadID);

       extras = getIntent().getExtras();
        if(extras != null) {
            final String phonenumber = extras.getString("PhNO");

            pName.setText(database.getData(phonenumber).get(0));
            pPhone.setText(database.getData(phonenumber).get(1));
            pAge.setText(database.getData(phonenumber).get(2));
            pBg.setText(database.getData(phonenumber).get(3));
            pCity.setText(database.getData(phonenumber).get(4));

           update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v)
               {
                   Intent intent = new Intent(DonorProfile.this,UpdateProfile.class);
                   intent.putExtra("Name",pName.getText());
                   intent.putExtra("PhNo",pPhone.getText());
                   intent.putExtra("Age",pAge.getText());
                   intent.putExtra("Bg",pBg.getText());
                   intent.putExtra("City",pCity.getText());
                   startActivity(intent);

               }
           });


           delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                   database.deleteAccount(phonenumber);
                   startActivity(new Intent(DonorProfile.this,LoginActivity.class));
                   Toast.makeText(DonorProfile.this,"Account deleted successfully",Toast.LENGTH_SHORT).show();
                   finish();

                }
            });
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }

}
