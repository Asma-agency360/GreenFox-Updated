package com.example.aptech.greenfox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleSmoothies extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView rec;
    ArrayList<itemStore> list;
    MyRecyclerAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_smoothies);
        rec=(RecyclerView) findViewById(R.id.id_recycler_smoothies);
        rec.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<itemStore>();

        ref= FirebaseDatabase.getInstance().getReference().child("5");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren())
                {
                    itemStore i=snap.getValue(itemStore.class);
                    list.add(i);
                }
                adapter1=new MyRecyclerAdapter(RecycleSmoothies.this,list);
                adapter1.notifyDataSetChanged();
                rec.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecycleSmoothies.this, "Opsssssss.......", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
