package com.saulh.vehicles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CarDetailMain extends AppCompatActivity {

    TextView make, detail, price, lastUpdate;
    ImageView img;
    private final String TAG = CarDetailMain.class.getSimpleName();
    HashMap<String, String> temp;
    String carDetailByIDPrefix = "https://thawing-beach-68207.herokuapp.com/cars/";

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

        Intent intent = getIntent();
        Bundle bun = intent.getExtras();

        String car_id;

        if(bun != null){
            car_id = (String) bun.get("CarID");
            System.out.println("sucess: "+ "carID: " + car_id );
            new GetCarDetailByID(carDetailByIDPrefix + car_id).execute();

        }

        new New_Thread().execute();

        //call asynctask

        /*
        System.out.println("Print from CarDetailMain");
        for(String tem: temp.keySet())   {
            System.out.println("key: " + tem );
        }

        for(String tem: temp.values())   {
            System.out.println(tem );
        }
        */

        //String this_make = MainActivity.thisCarDetailByID.get("make");
        //make.setText(this_make);

        //price.setText(MainActivity.thisCarDetailByID.get("price") );
        //lastUpdate.setText(MainActivity.thisCarDetailByID.get("updated_at"));


    }

    private class New_Thread extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(500);
                System.out.println("sucess222: "+ "carID: " +MainActivity.thisCarDetailByID.get("id") );
                String this_detail, this_make, this_price;
                this_detail = MainActivity.thisCarDetailByID.get("veh_description");
                detail.setText(this_detail);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}