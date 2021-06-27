package com.saulh.vehicles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static  String url = "https://thawing-beach-68207.herokuapp.com/carmakes";

    private Spinner spinnerMake;

    private ProgressDialog pDialog;

    HashMap<String, String> carMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carMake = new HashMap<>();
        spinnerMake = findViewById(R.id.spinnerMake);
        new GetCars().execute();

    }

    private class GetCars extends AsyncTask<Void, Void, Void> {
        private final String TAG = GetCars.class.getSimpleName();
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if(jsonStr != null)
            {
                try {
                    //JSONObject jsonobj = new JSONObject(jsonStr);

                    //Calling JSon array
                    JSONArray cars = new JSONArray(jsonStr);

                    for(int i = 0; i < cars.length();i++){
                        JSONObject d = cars.getJSONObject(i);

                        String id = d.getString("id");
                        String vehicle_make = d.getString("vehicle_make");

                        carMake.put(id, vehicle_make);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }

            ArrayList<String> arrayList_interface = new ArrayList<>();

            for(String key: carMake.keySet()){
                String value = carMake.get(key);
                arrayList_interface.add(value);
            }

            for(int i = 0; i < arrayList_interface.size(); i++){
                System.out.println("Print arraylist"+arrayList_interface.get(i));
            }

            /*
            spinnerMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "ITem chose", Toast.LENGTH_SHORT).show();
                }
            });*/

            ArrayAdapter<String> makeArray = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, arrayList_interface  );
            //makeArray.setDropDownViewResource(R.layout.spinner_item);
            spinnerMake.setAdapter(makeArray);

            spinnerMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"Say sth", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
}