package com.saulh.vehicles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CarDetailMain extends AppCompatActivity {

    private TextView make, detail, price, last_update;
    Button home;
    ImageView img;
    private final String TAG = CarDetailMain.class.getSimpleName();
    HashMap<String, String> temp;
    String carDetailByIDPrefix = "https://thawing-beach-68207.herokuapp.com/cars/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        make = findViewById(R.id.txtMakeModel);
        detail = findViewById(R.id.txtDetails);
        price = findViewById(R.id.txtPrice);
        last_update = findViewById(R.id.last_update);
        home = findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bun = intent.getExtras();

        String car_id;

        if(bun != null){
            car_id = (String) bun.get("CarID");
            new GetCarDetailByID(carDetailByIDPrefix + car_id).execute();

        }

        new New_Thread().execute();

    }

    private class New_Thread extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(250);
                System.out.println("sucess222: "+ "carID: " +MainActivity.thisCarDetailByID.get("id") );

                String this_detail, this_make, this_make_id, this_model_id, this_model, this_price, this_last_update;

                this_make_id = MainActivity.thisCarDetailByID.get("vehicle_make_id");//get car make id
                this_model_id = MainActivity.thisCarDetailByID.get("vehicle_model_id");//get car model id
                this_detail = MainActivity.thisCarDetailByID.get("veh_description");//get car descripton

                this_make = MainActivity.carMake.get(this_make_id);

                detail.setText(this_detail);

                this_last_update = " ";
                this_last_update = MainActivity.thisCarDetailByID.get("updated_at");
                last_update.setText("Last Updated: "+this_last_update);

                //use make id to get make from carMake
                this_make = MainActivity.carMake.get(this_make_id);

                this_price = MainActivity.thisCarDetailByID.get("price");
                price.setText("$" + this_price + "0");


                //use car model id to get model from models
                for(HashMap<String, String> temp: MainActivity.models){
                    for(String read: temp.keySet()){
                        if(temp.get(read).equals(this_model_id)){
                            this_model = temp.get("model");
                            make.setText("Make: "+this_make+ " "+ this_model);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}