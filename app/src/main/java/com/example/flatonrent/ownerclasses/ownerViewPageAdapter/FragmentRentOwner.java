package com.example.flatonrent.ownerclasses.ownerViewPageAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.myProperty.owner_after_clicking_hisproperty;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.tenantclasses.tenant_after_clicking_property_fromhome1.tenant_after_clicking_property_from_thome1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentRentOwner extends Fragment {
    DatabaseReference ref;
    String uid;
    String flataddress1, flatno1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, floor1, furnished1, parking1, tenant1, state1;
    String str,rent1,deposit1,maintance1;
    flatDetails fd;
    SwipeRefreshLayout swipeRefresh;

    //EditText ren,dep,mai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.activity_fragment_rent_owner, null);
        final TextView ren= ((TextView) view.findViewById(R.id.rent));
        final TextView dep= ((TextView) view.findViewById(R.id.deposit));
        final TextView mai= ((TextView) view.findViewById(R.id.maintance));
        ren.setText("ihdbeikfmenfuoiej");



        // Toast.makeText(getActivity().getApplicationContext(), uid, Toast.LENGTH_SHORT).show();

            uid = ((owner_after_clicking_hisproperty)getActivity()).getStrouid();



        ref = FirebaseDatabase.getInstance().getReference().child("owners").child(uid).child("fd");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // fd = dataSnapshot.getValue(flatDetails.class);database = FirebaseDatabase.getInstance();
                //            ref = database.getReference().child("owners").child(ouid);
                //            ref.child("nf").child(tuid).child("Time").setValue(time);
                //            ref.child("nf").child(tuid).child("Date").setValue(date);
                //            ref.child("nf").child(tuid).child("Status").setValue("pending");
                //            ref.child("nf").child(tuid).child("tuid").setValue(tuid);
                // Toast.makeText(getActivity().getApplicationContext(), "Inside data snapshot", Toast.LENGTH_SHORT).show();
                rent1 = dataSnapshot.child("rent").getValue(Long.class).toString();
                deposit1 = dataSnapshot.child("deposit").getValue(Long.class).toString();
                maintance1 = dataSnapshot.child("maintainence").getValue(Long.class).toString();
                ren.setText(rent1);
                dep.setText(deposit1);
                mai.setText(maintance1);

                //Toast.makeText(getActivity().getApplicationContext(), rent1, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });




        return view;
    }

}
