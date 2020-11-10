package com.example.aptech.greenfox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

   Context context;
   ArrayList<itemStore>  items_list;

   public MyRecyclerAdapter(Context c,ArrayList list_item)
   {
       context=c;
       items_list=list_item;
   }

    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_list_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerAdapter.MyViewHolder holder,final int position) {

       holder.t_name.setText("Name: "+items_list.get(position).getItemName());
       holder.t_price.setText("Price: "+items_list.get(position).getPrice());
       Picasso.get().load(items_list.get(position).getImage()).into(holder.img);
       final String pos;

        //update the recycler item
        holder.b_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialog_plus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_dialog_content))
                        .setExpanded(true,1200)
                        .create();
                //dialog_plus.show();

                View myview=dialog_plus.getHolderView();
                final Uri fileUri;
                final EditText iurl=myview.findViewById(R.id.uimgurl);
                final EditText name=myview.findViewById(R.id.item_name_update);
                final EditText price=myview.findViewById(R.id.item_price_update);
                final Button submit=myview.findViewById(R.id.btn_submit_update);
                final EditText postid=myview.findViewById(R.id.postid);
                final  EditText categoryid=myview.findViewById(R.id.categoryID);


                iurl.setText(items_list.get(position).getImage());
                name.setText(items_list.get(position).getItemName());
                price.setText(items_list.get(position).getPrice());
                postid.setText(items_list.get(position).getPostID());
                categoryid.setText(items_list.get(position).getCategory());

                dialog_plus.show();

                final String cat=categoryid.getText().toString();
                final String POSTID=postid.getText().toString();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = mFirebaseDatabase.getReference().child(cat);
                        //String postID=items_list.get(position).getPostID();
                        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("1");

                        Map<String,Object> map=new HashMap<>();
                        map.put("Image",iurl.getText().toString());
                        map.put("ItemName",name.getText().toString());
                        map.put("Price",price.getText().toString());
                        map.put("postID",postid.getText().toString());
                        map.put("Category",categoryid.getText().toString());


                        final  String POSTID=postid.getText().toString();

                       // databaseReference.child(POSTID).updateChildren(map);
                        FirebaseDatabase.getInstance().getReference().child(cat)
                                .child(POSTID).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                dialog_plus.dismiss();
                                // view.getContext().startActivity(new Intent(context, Items_CardView.class));
                                Toast.makeText(context, "Item Updated Successfully in Category " +cat, Toast.LENGTH_SHORT).show();
                                if(cat.equals("1"))
                                {
                                    view.getContext().startActivity(new Intent(context, ItemListRecycler.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat.equals("2"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleLunch.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat.equals("3"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleWeekends.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat.equals("4"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleSideDish.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat.equals("5"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleSmoothies.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat.equals("6"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleDrinks.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }


                                //view.getContext().startActivity(new Intent(context, ItemListRecycler.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                notifyDataSetChanged();
                                notifyItemChanged(position);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                dialog_plus.dismiss();
                                Toast.makeText(context, "Not Updated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                        });

                     /*   databaseReference.child(POSTID).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        notifyItemChanged(position);
                                        dialog_plus.dismiss();
                                        notifyDataSetChanged();
                                        notifyItemChanged(position);
                                       // view.getContext().startActivity(new Intent(context, Items_CardView.class));
                                        Toast.makeText(context, "Item Updated Successfully in Category " +cat, Toast.LENGTH_SHORT).show();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog_plus.dismiss();
                                Toast.makeText(context, "Not Updated Successfully...", Toast.LENGTH_SHORT).show();

                            }
                        });


                      */
                    }
                });

            }
        });

        holder.b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete.....?");;


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String cat1=items_list.get(position).getCategory().toString();
                        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = mFirebaseDatabase.getReference().child(cat1);
                        final String posit=items_list.get(position).getPostID().toString();


                        databaseReference.child(posit).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //view.getContext().startActivity(new Intent(context, Items_CardView.class));
                                notifyItemRemoved(position);
                                Toast.makeText(context, "Item Deleted from Category " +cat1, Toast.LENGTH_SHORT).show();
                                if(cat1.equals("1"))
                                {
                                    view.getContext().startActivity(new Intent(context, ItemListRecycler.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat1.equals("2"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleLunch.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat1.equals("3"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleWeekends.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat1.equals("4"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleSideDish.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat1.equals("5"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleSmoothies.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                                if(cat1.equals("6"))
                                {
                                    view.getContext().startActivity(new Intent(context, RecycleDrinks.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Not Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView t_name,t_price,t_post;
       ImageView img;
       ImageButton b_edit,b_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            t_name=(TextView) itemView.findViewById(R.id.item_name_in_recycle);
            t_price=(TextView) itemView.findViewById(R.id.item_price_in_recycle);
            img=(ImageView) itemView.findViewById(R.id.image_recycle);

            b_edit=(ImageButton) itemView.findViewById(R.id.btn_edit_record);
            b_delete=(ImageButton) itemView.findViewById(R.id.btn_delete_record);


        }
    }
}
