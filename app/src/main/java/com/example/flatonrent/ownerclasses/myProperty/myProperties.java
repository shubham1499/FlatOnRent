package com.example.flatonrent.ownerclasses.myProperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myProperties extends AppCompatActivity {

    DatabaseReference databaseReference,changeref;

    ProgressDialog progressDialog;

    List<flatDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    String uid;
    SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("mypropertiesopen","ok!!!");


        setContentView(R.layout.activity_my_properties);

        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(myProperties.this));


        progressDialog = new ProgressDialog(myProperties.this);

        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();

        swipeRefresh= findViewById(R.id.refreshLayout);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databaseReference = FirebaseDatabase.getInstance().getReference("owners").child(uid).child("fd");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        list.clear();
//                        for (DataSnapshot p : snapshot.getChildren()) {
//                            Log.i("snapshot.children", snapshot.getChildrenCount() + "");
//                            for (DataSnapshot q : p.getChildren()) {
//                                Log.i("p.children:", p.getChildrenCount() + "");
//                                Log.i("q.children:", q.getChildrenCount() + "");
//                                if (q.child("flag").getValue().toString().equals("true")) {
//                                    list.add(q.getValue(flatDetails.class));
//                                }
//                            }
//                        }

                        if(snapshot.child("flag").getValue(Boolean.class)) {
                            list.add(snapshot.getValue(flatDetails.class));
                        }
                        adapter = new RecyclerViewAdapterOwnerProperties(myProperties.this, list);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
                swipeRefresh.setRefreshing(false);
            }
        });
            databaseReference = FirebaseDatabase.getInstance().getReference("owners").child(uid).child("fd");
          //databaseReference = FirebaseDatabase.getInstance().getReference("owners");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                        list.clear();

                        Log.i("initial data fetching","ok");
//                        for (DataSnapshot p : snapshot.getChildren()) {
//                            Log.i("snapshot.children", snapshot.getChildrenCount() + "");
//                            for (DataSnapshot q : p.getChildren()) {
//                                Log.i("p.children:", p.getChildrenCount() + "");
//                                Log.i("q.children:", q.getChildrenCount() + "");
//
//                                if (q.child("flag").getValue().toString().equals("true")) {
//                                    list.add(q.getValue(flatDetails.class));
//                                }
//                            }
//                        }
                    if(snapshot.child("flag").getValue(Boolean.class)) {
                        list.add(snapshot.getValue(flatDetails.class));
                    }

                        Log.i("data fetching done?","ok");
                        adapter = new RecyclerViewAdapterOwnerProperties(myProperties.this, list);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
    }
}