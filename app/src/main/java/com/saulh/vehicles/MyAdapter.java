package com.saulh.vehicles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Car> car_list;

    public MyAdapter(Context ct, ArrayList<Car> car_list){
        this.context = ct;
        this.car_list = car_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.car_item = car_list.get(position);
        holder.car_row_price.setText("$ " + car_list.get(position).price + "0");
        holder.car_row_mileage.setText(car_list.get(position).mileage + " miles");
        holder.car_row_model.setText(car_list.get(position).model);
    }

    @Override
    public int getItemCount() {
        return car_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView car_row_img;
        TextView car_row_price, car_row_mileage, car_row_model;
        Car car_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            car_row_img = itemView.findViewById(R.id.car_row_imageView);
            car_row_price = itemView.findViewById(R.id.car_row_price);
            car_row_mileage = itemView.findViewById(R.id.car_row_mileage);
            car_row_model = itemView.findViewById(R.id.car_row_model);
        }
    }
}
