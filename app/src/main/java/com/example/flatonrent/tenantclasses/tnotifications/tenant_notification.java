package com.example.flatonrent.tenantclasses.tnotifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.notification.RecyclerViewAdapterOwnerNotification;
import com.example.flatonrent.ownerclasses.ownerDataFields.ownerRequests;
import com.example.flatonrent.tenantclasses.tenantDataFields.tenantRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tenant_notification extends Fragment {
    @Nullable
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;

    String uid;

    SwipeRefreshLayout swipeRefresh;
    ProgressDialog progressDialog;
    DatabaseReference ref;

    List<tenantRequests> list = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.activity_tenant_notification, null);

        ((TextView)root.findViewById(R.id.tvrequeststatus)).setVisibility(View.GONE);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        progressDialog = new ProgressDialog(this.getContext());

        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();
        final Context contexthere=this.getContext();

        ref= FirebaseDatabase.getInstance().getReference().child("tenants").child(uid).child("nf");

        swipeRefresh = root.findViewById(R.id.refreshLayout);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        if(dataSnapshot.getValue()!=null)
                        {
                            Log.i("dataSnapShotChildcount",dataSnapshot.getChildrenCount()+"");
                            for(DataSnapshot p : dataSnapshot.getChildren())
                            {
                                Log.i("p.getchildrencount",p.getChildrenCount()+"");
                                list.add(p.getValue(tenantRequests.class));
                            }
                        }
                        else
                        {
                            ((TextView)root.findViewById(R.id.tvrequeststatus)).setVisibility(View.VISIBLE);
                        }
                        Log.i("data fetching done?", "ok");

                        if(!list.isEmpty()) {
                            adapter = new RecyclerViewAdapterTenantNotification(contexthere, list);
                            recyclerView.setAdapter(adapter);
                        }
                        progressDialog.dismiss();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
                swipeRefresh.setRefreshing(false);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if (dataSnapshot.getValue() != null) {
                    Log.i("dataSnapShotChildcount", dataSnapshot.getChildrenCount() + "");
                    for (DataSnapshot p : dataSnapshot.getChildren()) {
                        Log.i("p.getchildrencount", p.getChildrenCount() + "");
                        list.add(p.getValue(tenantRequests.class));
                    }
                } else {
                    ((TextView) root.findViewById(R.id.tvrequeststatus)).setVisibility(View.VISIBLE);
                }
                Log.i("data fetching done?", "ok");
                if (!list.isEmpty()) {
                    adapter = new RecyclerViewAdapterTenantNotification(contexthere, list);
                    recyclerView.setAdapter(adapter);
                }
                progressDialog.dismiss();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        return root;
    }
}