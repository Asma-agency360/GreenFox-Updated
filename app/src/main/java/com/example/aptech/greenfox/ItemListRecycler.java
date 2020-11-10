package com.example.aptech.greenfox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListRecycler extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView rec;
    ArrayList<itemStore> list;
    MyRecyclerAdapter adapter1;
    RecyclerFirebaseADapter fadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_recycler);
        rec=(RecyclerView) findViewById(R.id.id_recycler1);
        rec.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<itemStore>();

        ref= FirebaseDatabase.getInstance().getReference().child("1");
/*
        FirebaseRecyclerOptions<itemStore> options =
                new FirebaseRecyclerOptions.Builder<itemStore>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("1"), itemStore.class)
                        .build();

        fadapter=new RecyclerFirebaseADapter(options);
        fadapter.startListening();
        rec.setAdapter(fadapter);
       /*

 */
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren())
                {
                    itemStore i=snap.getValue(itemStore.class);
                    list.add(i);
                }
                adapter1=new MyRecyclerAdapter(ItemListRecycler.this,list);
                adapter1.notifyDataSetChanged();
                rec.setAdapter(adapter1);


/*
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

 */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemListRecycler.this, "Opsssssss.......", Toast.LENGTH_SHORT).show();

            }
        });


    }

/*
    @Override
    protected void onStop() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        super.onStop();

    }
    */


}
