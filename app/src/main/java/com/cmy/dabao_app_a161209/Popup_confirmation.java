package com.cmy.dabao_app_a161209;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Popup_confirmation extends Activity {
    Button btnConfirm, btnCancel;
    TextView tvDriverName, tvFrom, tvTo, tvFoodPrice, tvDeliveryFee, tvTotal;
    String orderUid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_confirmation);

        btnConfirm = findViewById(R.id.btn_confirmorder);
        btnCancel = findViewById(R.id.btn_cancel);
        tvDriverName = findViewById(R.id.tv_driverName);
        tvFrom = findViewById(R.id.tv_from);
        tvTo = findViewById(R.id.tv_to);
        tvFoodPrice = findViewById(R.id.tv_foodPrice);
        tvDeliveryFee = findViewById(R.id.tv_deliveryFee);
        tvTotal = findViewById(R.id.tv_total);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9), (int)(height*.9));

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");
        ref.orderByChild("hunterUid").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    orderUid = childSnapshot.getKey();
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Order").child(orderUid);
                    ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Order details = dataSnapshot.getValue(Order.class);
                            tvDriverName.setText("Driver Name: " + details.getDriverName());
                            tvFrom.setText("From: " + details.getFrom());
                            tvTo.setText("To: " + details.getTo());
                            tvFoodPrice.setText("Food Price: RM" + details.getFoodPrice().toString());
                            tvDeliveryFee.setText("Delivery Fee: RM" + details.getDeliveryFee().toString());
                            tvTotal.setText("Total: RM" + details.getTotal().toString());
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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Popup_confirmation.this, Food_tracking.class));
            }
        });

    }

}
