package com.example.flatonrent.tenantclasses.tnotifications;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.notification.owner_notification_afterclicking_tenant;
import com.example.flatonrent.tenantclasses.tenantDataFields.tenantRequests;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterTenantNotification extends RecyclerView.Adapter<RecyclerViewAdapterTenantNotification.ViewHolder> {
    String emailidofowner;
    Context context;
    List<tenantRequests> MainImageUploadInfoList;

    public RecyclerViewAdapterTenantNotification(Context context, List<tenantRequests> TempList) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_tenantrequest_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        tenantRequests tr = MainImageUploadInfoList.get(position);

        Log.i("()gettime()",tr.getTime());
        holder.tvttimehere.setText(tr.getTime());

        holder.tvtdatehere.setText(tr.getDate());
        holder.tvouidhere.setText(tr.getOuid());
        holder.tvtstatushere.setText(tr.getStatus());
        holder.trhere=tr;

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Log.i("shubham","infirebaseauth");
            Log.i("owneruid",tr.getOuid());
            StorageReference iref = FirebaseStorage.getInstance().getReference().child("owners").child(tr.getOuid())
                    .child("profilepic").child("myPhoto.jpg");
            iref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(context!=null)
                    {
                        Log.i("shubham","ingetapplicationcontext");
                        holder.ownerimagehere.setImageDrawable(null);
                        Picasso.with(context).load(uri.toString()).into(holder.ownerimagehere);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }





        Log.i("gettuid",tr.getOuid()+"123");

        DatabaseReference tempref= FirebaseDatabase.getInstance().getReference().child("owners").child(tr.getOuid())
                .child("pd");

        tempref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fname,lname,phone;
                fname = dataSnapshot.child("firstname").getValue(String.class);
                lname = dataSnapshot.child("lastname").getValue(String.class);
                phone = dataSnapshot.child("phonenumber").getValue(String.class);
                emailidofowner = dataSnapshot.child("emailid").getValue(String.class);
                holder.tvownernamehere.setText(fname+" "+lname+"("+phone+")");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        holder.mycardviewhere.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, own.class);
//                i.putExtra("selectedtenantuid",holder.tvouidhere.getText().toString());
//                context.startActivity(i);
//            }
//        });
        holder.mycardviewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,emailidofowner,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public tenantRequests trhere;
        public TextView tvouidhere;
        public TextView tvownernamehere;
        public TextView tvtdatehere;
        public TextView tvttimehere;
        public TextView tvtstatushere;
        public SelectableRoundedImageView ownerimagehere;
        CardView mycardviewhere;
        Context contexttemp;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            contexttemp=itemView.getContext();
            ownerimagehere = itemView.findViewById(R.id.ownerimage);

            tvownernamehere = (TextView) itemView.findViewById(R.id.tvownername);

            tvtdatehere = (TextView) itemView.findViewById(R.id.tvtdate);

            tvttimehere=(TextView)  itemView.findViewById(R.id.tvttime);

            tvouidhere=(TextView)itemView.findViewById(R.id.tvouid);

            tvtstatushere=(TextView)itemView.findViewById(R.id.tvtstatus);

            mycardviewhere = itemView.findViewById(R.id.mycardview);
        }

        @Override
        public void onClick(View view) {
            String str=((TextView)view.findViewById(R.id.tvouid)).getText().toString();
            Toast.makeText(view.getContext(),str+"   "+getLayoutPosition(),Toast.LENGTH_SHORT).show();
        }
    }
}