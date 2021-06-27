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
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static  String make_url = "https://thawing-beach-68207.herokuapp.com/carmakes";
    private static String model_url_prefix = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";

    private Spinner spinnerMake;
    private Spinner spinnerModel;

    private ProgressDialog pDialog;

    HashMap<String, String> carMake;

    private String get_car_id(HashMap<String, String> carMake, String car_name){
        String key = " ";
        for(String i: carMake.keySet()){
            System.out.println("Debug get_car_id" + carMake.get(i) +"  "+ car_name);
            if(carMake.get(i).toString() == car_name){
                key = i;
            }
        }
        return key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carMake = new HashMap<>();
        spinnerMake = findViewById(R.id.spinnerMake);
        spinnerModel = findViewById(R.id.spinnerModel);
        new GetCars().execute();
        //new GetModel(model_url_prefix).execute();

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

            String jsonStr = sh.makeServiceCall(make_url);

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

            ArrayAdapter<String> makeArray = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, arrayList_interface  );
            //makeArray.setDropDownViewResource(R.layout.spinner_item);
            spinnerMake.setAdapter(makeArray);

            spinnerMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected_item = spinnerMake.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),selected_item, Toast.LENGTH_SHORT).show();
                    String this_key = get_car_id(carMake,selected_item);
                    new GetModel(model_url_prefix, this_key).execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    private class GetModel extends AsyncTask<Void, Void, Void> {

        private String url;
        private ArrayList<HashMap<String, String>> models;

        public GetModel(String model_url, String model_id){
            this.url = model_url + model_id;
        }
        private final String TAG = GetModel.class.getSimpleName();
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
            models = new ArrayList<>();

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
                        String model = d.getString("model");
                        String vehicle_make_id = d.getString("vehicle_make_id");

                        HashMap<String, String> bridge = new HashMap<>();

                        bridge.put("id", id);
                        bridge.put("model", model);
                        bridge.put("vehicle_make_id", vehicle_make_id);

                        models.add(bridge);

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

            ArrayList<String> arrayList_interface = new ArrayList<>(); //here is the models' names

            for(HashMap<String, String> temp: models){
                arrayList_interface.add(temp.get("model"));


            }


            ArrayAdapter<String> makeArray = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, arrayList_interface  );
            //makeArray.setDropDownViewResource(R.layout.spinner_item);
            spinnerModel.setAdapter(makeArray);

            spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected_item = spinnerModel.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),selected_item, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

}