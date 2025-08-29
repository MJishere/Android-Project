package com.example.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class DonorsList extends AppCompatActivity {

    DonorsDatabase database = new DonorsDatabase(this);
    ListView donorlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);
        fillListView();
    }
    public void fillListView()
    {
        String city = getIntent().getExtras().getString("city").toString();
        String bg = getIntent().getExtras().getString("bg").toString();
        donorlistView = findViewById(R.id.donorListViewID);
        DonorsDatabase database = new DonorsDatabase(this);
        ArrayList<SingleRow> list = database.getDonors(city,bg);
        MyAdapter myAdapter = new MyAdapter(list,this);
        if (myAdapter.getCount()!=0) {
            donorlistView.setAdapter(myAdapter);
        }
        else
        Toast.makeText(this,"No donors found !",Toast.LENGTH_LONG).show();

    }

}



 class SingleRow  {

    private String name;
    private String phonenumber;


    public SingleRow(String name, String phonenumber) {
        this.name = name;
        this.phonenumber = phonenumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}



class MyAdapter extends BaseAdapter{
    Context context;

    ArrayList<SingleRow>  list;

    public MyAdapter(ArrayList<SingleRow> list,Context cont)
    {
        this.list = list;
        this.context = cont;

    }

    @Override
    public int getCount() {
        return this.list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.donor_list, null);
            TextView name = (TextView) convertView.findViewById(R.id.dNameID);
            TextView phonenumber = (TextView) convertView.findViewById(R.id.dPhoneNumberID);
            ImageButton callButton = (ImageButton) convertView.findViewById(R.id.callButtonID);

            final SingleRow singleRow = list.get(position);

            name.setText("Name : " + singleRow.getName());
            phonenumber.setText("Ph No : " + singleRow.getPhonenumber());
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneDialerIntent = new Intent(Intent.ACTION_DIAL);
                    phoneDialerIntent.setData(Uri.parse("tel:"+singleRow.getPhonenumber()));
                    context.startActivity(phoneDialerIntent);
                }
            });
        }

        return convertView;

    }
}