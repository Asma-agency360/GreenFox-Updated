package com.example.aptech.greenfox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_dashboard extends AppCompatActivity {

    CardView c_add_item,c_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        c_add_item=findViewById(R.id.card_add);
        c_list_view=findViewById(R.id.card_list);

        c_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Insert_menu_items.class);
                startActivity(i);
            }
        });


        c_list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Items_CardView.class);
                startActivity(i);
            }
        });

    }
}
