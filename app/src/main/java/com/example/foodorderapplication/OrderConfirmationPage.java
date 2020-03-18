package com.example.foodorderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.example.foodorderapplication.FinalCartPreview.temparraylist;

import java.util.ArrayList;

public class OrderConfirmationPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private  FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation_page);
        listView=(ListView)findViewById(R.id.listview_order);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("Card Details");
        FirebaseUser user = mAuth.getCurrentUser();
        userId=user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds :dataSnapshot.getChildren())
        {
            BookMyOrderModel bookMyOrderModel = new BookMyOrderModel();
            bookMyOrderModel.setName(ds.child(userId).getValue(BookMyOrderModel.class).getName());
            bookMyOrderModel.setAddress(ds.child(userId).getValue(BookMyOrderModel.class).getAddress());
            bookMyOrderModel.setPhonenumber(ds.child(userId).getValue(BookMyOrderModel.class).getPhonenumber());
            bookMyOrderModel.setPincode(ds.child(userId).getValue(BookMyOrderModel.class).getPincode());
            bookMyOrderModel.setTotalamount(ds.child(userId).getValue(BookMyOrderModel.class).getTotalamount());
/*          bookMyOrderModel.setPhonenumber(ds.child(userId).getValue(BookMyOrderModel.class).getPhonenumber());
            bookMyOrderModel.setPhonenumber(ds.child(userId).getValue(BookMyOrderModel.class).getPhonenumber());
            bookMyOrderModel.setPhonenumber(ds.child(userId).getValue(BookMyOrderModel.class).getPhonenumber());*/
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add(bookMyOrderModel.getName());
            arrayList.add(bookMyOrderModel.getAddress());
            arrayList.add(bookMyOrderModel.getPhonenumber());
            arrayList.add(bookMyOrderModel.getPincode());
            arrayList.add(String.valueOf(bookMyOrderModel.getTotalamount()));
            ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

            /*else
            {
                finish();
                startActivity(new Intent(this,ProfileActivity.class));
            }*/

        }
    }
}