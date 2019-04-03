package com.cmy.dabao_app_a161209;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Restaurant_adapter extends RecyclerView.Adapter<Restaurant_adapter.MyViewHolder> {

    Context context;
    ArrayList<Restaurant_location> restaurant;

    public Restaurant_adapter(Context c, ArrayList<Restaurant_location> r){
        context = c;
        restaurant = r;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_restaurant_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.Username.setText(restaurant.get(i).getUsername());
        myViewHolder.RestaurantName.setText(restaurant.get(i).getRestaurantName());
        myViewHolder.FoodTag1.setText(restaurant.get(i).getFoodTag1());
        myViewHolder.FoodTag2.setText(restaurant.get(i).getFoodTag2());
        //Picasso.get().load.(restaurant.get(i).getProfilePic()).into(myViewHolder.profilePic);

    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView RestaurantName, FoodTag1, FoodTag2,Username;
        //ImageView profilePic;
        Button btnOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = (TextView)itemView.findViewById(R.id.tv_username);
            RestaurantName = (TextView)itemView.findViewById(R.id.tv_restaurant);
            FoodTag1 = (TextView)itemView.findViewById(R.id.tv_foodtag1);
            FoodTag2 = (TextView)itemView.findViewById(R.id.tv_foodtag2);
            btnOrder = (Button) itemView.findViewById(R.id.btn_takeorder);
        }

        public void onClick(int position){
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Contact Driver!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
