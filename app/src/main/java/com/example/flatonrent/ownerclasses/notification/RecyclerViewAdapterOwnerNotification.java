package com.example.flatonrent.ownerclasses.notification;
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
import com.example.flatonrent.ownerclasses.ownerDataFields.ownerRequests;
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

public class RecyclerViewAdapterOwnerNotification extends RecyclerView.Adapter<RecyclerViewAdapterOwnerNotification.ViewHolder> {

    Context context;
    List<ownerRequests> MainImageUploadInfoList;

    public RecyclerViewAdapterOwnerNotification(Context context, List<ownerRequests> TempList) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_ownerrequest_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ownerRequests or = MainImageUploadInfoList.get(position);

          Log.i("()gettime()",or.getTime());
          holder.tvtimehere.setText(or.getTime());

          holder.tvdatehere.setText(or.getDate());
          holder.tvtuidhere.setText(or.gettuid());
          holder.tvstatushere.setText(or.getStatus());
          holder.orhere=or;




        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Log.i("shubham","infirebaseauth");
            Log.i("owneruid",or.gettuid());
            StorageReference iref = FirebaseStorage.getInstance().getReference().child("tenants").child(or.gettuid())
                    .child("profilepic").child("myPhoto.jpg");
            iref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(context!=null)
                    {
                        Log.i("shubham","ingetapplicationcontext");
                        holder.tenantimagehere.setImageDrawable(null);
                        Picasso.with(context).load(uri.toString()).into(holder.tenantimagehere);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
          Log.i("gettuid",or.gettuid()+"123");

          DatabaseReference tempref= FirebaseDatabase.getInstance().getReference().child("tenants").child(or.gettuid())
                .child("pd");

        tempref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fname,lname;
                fname = dataSnapshot.child("firstname").getValue(String.class);
                lname = dataSnapshot.child("lastname").getValue(String.class);
                holder.tvtenantnamehere.setText(fname+" "+lname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.mycardviewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,owner_notification_afterclicking_tenant.class);
                i.putExtra("selectedtenantuid",holder.tvtuidhere.getText().toString());
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ownerRequests orhere;
        public TextView tvtuidhere;
        public TextView tvtenantnamehere;
        public TextView tvdatehere;
        public TextView tvtimehere;
        public TextView tvstatushere;
        public SelectableRoundedImageView tenantimagehere;
        CardView mycardviewhere;
        Context contexttemp;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            contexttemp=itemView.getContext();
            tenantimagehere = itemView.findViewById(R.id.tenantimage);

            tvtenantnamehere = (TextView) itemView.findViewById(R.id.tvtenantname);

            tvdatehere = (TextView) itemView.findViewById(R.id.tvdate);

            tvtimehere=(TextView)  itemView.findViewById(R.id.tvtime);

            tvtuidhere=(TextView)itemView.findViewById(R.id.tvtuid);

            tvstatushere=(TextView)itemView.findViewById(R.id.tvstatus);

            mycardviewhere = itemView.findViewById(R.id.mycardview);
        }

        @Override
        public void onClick(View view) {
            String str=((TextView)view.findViewById(R.id.tvtuid)).getText().toString();
            Toast.makeText(view.getContext(),str+"   "+getLayoutPosition(),Toast.LENGTH_SHORT).show();
        }
    }
}