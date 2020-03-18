package com.example.flatonrent.tenantclasses.thome1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import android.app.ProgressDialog;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.flatonrent.ownerclasses.myProperty.RecyclerViewAdapterOwnerProperties;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class thome_1 extends Fragment {
    DatabaseReference databaseReference, changeref;

    ProgressDialog progressDialog;

    List<flatDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    String uid;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.activity_thome_1, null);

        Log.i("mypropertiesopen", "ok!!!");

        Log.i("mypropertiesopen", "ok!!!");


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final Context contexthere=this.getContext();
        progressDialog = new ProgressDialog(this.getContext());

        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();

        swipeRefresh = root.findViewById(R.id.refreshLayout);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("on refresh:", "yes");

                databaseReference = FirebaseDatabase.getInstance().getReference("owners");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot p : snapshot.getChildren()) {
                            Log.i("snapshot.children", snapshot.getChildrenCount() + "");
                            for (DataSnapshot q : p.getChildren()) {
                                Log.i("p.children:", p.getChildrenCount() + "");
                                Log.i("q.children:", q.getChildrenCount() + "");

                                if(q.hasChild("flag") && q.hasChild("area")) {
                                    if (q.child("flag").getValue(Boolean.class)) {
                                        Log.i("q.children()", q.getChildrenCount() + "");
                                        list.add(q.getValue(flatDetails.class));
                                    }
                                }
                            }
                        }
                        adapter = new RecyclerViewAdapterTenantHome(contexthere,list);
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
        // databaseReference = FirebaseDatabase.getInstance().getReference("owners").child(uid);
        databaseReference = FirebaseDatabase.getInstance().getReference("owners");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();

                Log.i("initial data fetching", "ok");
                int i=0;
                for (DataSnapshot p : snapshot.getChildren()) {
                    Log.i("snapshot.children", snapshot.getChildrenCount() + "");
                    for (DataSnapshot q : p.getChildren()) {
                        Log.i("p.children:", p.getChildrenCount() + "");
                        Log.i("q.children:", q.getChildrenCount() + "");

                        if(q.hasChild("flag") && q.hasChild("area")) {
                            if (q.child("flag").getValue(Boolean.class)) {
                                Log.i("q.children()", q.getChildrenCount() + "");
                                list.add(q.getValue(flatDetails.class));
                                i++;
                            }
                        }
                    }
                }
                Log.i("iterator", i+"");
                Log.i("data fetching done?", "ok");
                adapter = new RecyclerViewAdapterTenantHome(contexthere, list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return root;
    }
}