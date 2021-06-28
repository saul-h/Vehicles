package com.saulh.vehicles;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GetCarDetailByID extends AsyncTask<Void, Void, Void> {
    public static HashMap<String, String> carDetailByID;
    private ProgressDialog pDialog;
    private final String TAG = GetCarDetailByID.class.getSimpleName();
    private String url;
    //Context context;
    TextView make, detail, price, lastUpdate;
    ImageView img;

    public GetCarDetailByID(String url){
        this.url = url;
        //this.context = ct;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
        /*
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait ...");
        pDialog.setCancelable(false);
        pDialog.show();*/
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        HttpHandler sh = new HttpHandler();
        carDetailByID = new HashMap<>();
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONArray cars = new JSONArray(jsonStr);
                for(int i = 0; i < cars.length();i++){
                    JSONObject d = cars.getJSONObject(i);

                    String carcondition_id = d.getString("carcondition_id");
                    String color_id = d.getString("color_id");
                    String content_local_url = d.getString("content_local_url");
                    String content_url = d.getString("content_url");
                    String created_at = d.getString("created_at");
                    String currency_id = d.getString("currency_id");
                    String id = d.getString("id");
                    String image_local_url = d.getString("image_local_url");
                    String image_url = d.getString("image_url");
                    String is_active = d.getString("is_active");
                    String mileage = d.getString("mileage");
                    String onlinecardealer_id = d.getString("onlinecardealer_id");
                    String price = d.getString("price");
                    String seller_address = d.getString("seller_address");
                    String seller_address_locality = d.getString("seller_address_locality");
                    String seller_address_region = d.getString("seller_address_region");
                    String seller_name = d.getString("seller_name");
                    String seller_telnumber = d.getString("seller_telnumber");
                    String updated_at = d.getString("updated_at");
                    String veh_description = d.getString("veh_description");
                    String vehicle_make_id = d.getString("vehicle_make_id");
                    String vehicle_model_id = d.getString("vehicle_model_id");
                    String vehicle_url = d.getString("vehicle_url");
                    String vin_number = d.getString("vin_number");
                    String zipcode_id = d.getString("zipcode_id");

                    carDetailByID.put("carcondition_id", carcondition_id);
                    carDetailByID.put("color_id", color_id);
                    carDetailByID.put("content_local_url", content_local_url);
                    carDetailByID.put("content_url", content_url);
                    carDetailByID.put("created_at", created_at);
                    carDetailByID.put("currency_id", currency_id);
                    carDetailByID.put("id", id);
                    carDetailByID.put("image_local_url", image_local_url);
                    carDetailByID.put("image_url", image_url);
                    carDetailByID.put("is_active", is_active);
                    carDetailByID.put("mileage", mileage);
                    carDetailByID.put("onlinecardealer_id", onlinecardealer_id);
                    carDetailByID.put("price", price);
                    carDetailByID.put("seller_address", seller_address);
                    carDetailByID.put("seller_address_locality", seller_address_locality);
                    carDetailByID.put("seller_address_region", seller_address_region);
                    carDetailByID.put("seller_name", seller_name);
                    carDetailByID.put("seller_telnumber", seller_telnumber);
                    carDetailByID.put("updated_at", updated_at);
                    carDetailByID.put("veh_description", veh_description);
                    carDetailByID.put("vehicle_make_id", vehicle_make_id);
                    carDetailByID.put("vehicle_model_id", vehicle_model_id);
                    carDetailByID.put("vehicle_url", vehicle_url);
                    carDetailByID.put("vin_number", vin_number);
                    carDetailByID.put("zipcode_id", zipcode_id);
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
        /*
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
         */

        MainActivity.thisCarDetailByID = carDetailByID;

        System.out.println("from GetCarDetailByID");
        for(String i: carDetailByID.values()){
            System.out.println(i);
        }


    }
}
