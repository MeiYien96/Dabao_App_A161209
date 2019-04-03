package com.cmy.dabao_app_a161209;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class User_adapter extends RecyclerView.Adapter<User_adapter.MyViewHolder> {

    Context context;
    ArrayList<User> driverAvailable;

    public User_adapter(Context c, ArrayList<User> d){
        context = c;
        driverAvailable = d;
    }
    @NonNull
    @Override
    public User_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new User_adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_restaurant_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull User_adapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.UserName.setText(driverAvailable.get(i).getUsername());
        //Picasso.get().load.(restaurant.get(i).getProfilePic()).into(myViewHolder.profilePic);

    }

    @Override
    public int getItemCount() {
        return driverAvailable.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView UserName;
        //ImageView profilePic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = (TextView)itemView.findViewById(R.id.tv_username);

        }

    }
}
