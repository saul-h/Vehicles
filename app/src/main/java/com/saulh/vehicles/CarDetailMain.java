package com.saulh.vehicles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class CarDetailMain extends AppCompatActivity {

    TextView make, detail, price, lastUpdate;
    ImageView img;
    private final String TAG = CarDetailMain.class.getSimpleName();
    HashMap<String, String> temp;

    public CarDetailMain(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail_main);

        make = findViewById(R.id.txtMakeModel);
        detail = findViewById(R.id.txtDetails);
        price = findViewById(R.id.txtPrice);
        lastUpdate = findViewById(R.id.txtLastUpdated);

        temp = new HashMap<>();
        temp = MainActivity.thisCarDetailByID;


        System.out.println("Print from CarDetailMain");
        for(String tem: temp.keySet())   {
            System.out.println("key: " + tem );
        }

        for(String tem: temp.values())   {
            System.out.println(tem );
        }

        //String this_make = MainActivity.thisCarDetailByID.get("make");
        //make.setText(this_make);

        detail.setText(temp.get("veh_description"));
        //price.setText(MainActivity.thisCarDetailByID.get("price") );
        //lastUpdate.setText(MainActivity.thisCarDetailByID.get("updated_at"));


    }
}