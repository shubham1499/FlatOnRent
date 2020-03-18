package com.example.flatonrent.tenantclasses.tenantViewPageAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.myProperty.owner_after_clicking_hisproperty;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.tenantclasses.tenant_after_clicking_property_fromhome1.tenant_after_clicking_property_from_thome1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentAddressTenant extends Fragment {
    DatabaseReference ref;
    View view;
    String uid;
    String flataddress1, flatno1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, rent1, deposit1, floor1, furnished1, maintance1, parking1, tenant1, state1;
    String str,landmark;
    flatDetails fd;


    public FragmentAddressTenant() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_address_tenant,container,false);

        {
            uid = ((tenant_after_clicking_property_from_thome1)getActivity()).getStrouid();
            ref = FirebaseDatabase.getInstance().getReference().child("owners").child(uid).child("fd");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    flataddress1 = dataSnapshot.child("flataddress").getValue(String.class);
                    fd = dataSnapshot.getValue(flatDetails.class);
                    flatno1 = dataSnapshot.child("flatno").getValue(Long.class).toString();
                    landmark = dataSnapshot.child("landmark").getValue(String.class);
                    apartment1 = dataSnapshot.child("apartmentname").getValue(String.class);
                    apartmentno1 = dataSnapshot.child("apartmentno").getValue(Long.class).toString();
                    area1 = dataSnapshot.child("area").getValue(String.class);
                    city1 = dataSnapshot.child("city").getValue(String.class);

                    ((TextView) view.findViewById(R.id.apartmentno)).setText(apartmentno1);
                    ((TextView) view.findViewById(R.id.area)).setText(area1);
                    ((TextView) view.findViewById(R.id.flatno)).setText(flatno1);
                    ((TextView) view.findViewById(R.id.flataddress)).setText(flataddress1);
                    ((TextView) view.findViewById(R.id.apartmentname)).setText(apartment1);
                    ((TextView) view.findViewById(R.id.city)).setText(city1);
                    ((TextView) view.findViewById(R.id.landmark)).setText(landmark);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return view;
    }

}
