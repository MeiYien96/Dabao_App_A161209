package com.cmy.dabao_app_a161209;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Restaurant_adapter extends RecyclerView.Adapter<Restaurant_adapter.MyViewHolder> {

    Context context;
    ArrayList<Restaurant_location> restaurant;
    String driverUid,driverPhone;

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
        if(restaurant.get(i).getProfilePic()==null){
            myViewHolder.profilePic.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }
        else{
            Picasso.get().load(restaurant.get(i).getProfilePic()).into(myViewHolder.profilePic);
        }
        myViewHolder.onClick(i);

    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView RestaurantName, FoodTag1, FoodTag2,Username;
        ImageView profilePic;
        Button btnOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = (TextView)itemView.findViewById(R.id.tv_username);
            RestaurantName = (TextView)itemView.findViewById(R.id.tv_restaurant);
            FoodTag1 = (TextView)itemView.findViewById(R.id.tv_foodtag1);
            FoodTag2 = (TextView)itemView.findViewById(R.id.tv_foodtag2);
            btnOrder = (Button) itemView.findViewById(R.id.btn_takeorder);
            profilePic = (ImageView)itemView.findViewById(R.id.iv_profile_pic);
        }

        public void onClick(final int position){
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Contact Driver "+restaurant.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("DriversAvailable");
                    myRef.orderByChild("username").equalTo(restaurant.get(position).getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                driverUid = childSnapshot.getKey();
                                Log.i(TAG,driverUid);
                                FirebaseDatabase.getInstance().getReference().child("HunterRequest").child(userId).child("driverUid").setValue(driverUid);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Food Driver").child(driverUid);
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        driverPhone = user.getPhone();
                                        String url = "https://api.whatsapp.com/send?phone="+driverPhone;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        context.startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }

}
