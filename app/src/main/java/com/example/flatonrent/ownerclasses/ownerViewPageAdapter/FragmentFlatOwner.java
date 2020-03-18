package com.example.flatonrent.ownerclasses.ownerViewPageAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.myProperty.owner_after_clicking_hisproperty;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentFlatOwner extends Fragment {
    DatabaseReference ref,ref1,ref2;
    String s1;
    Button deletePropertybyOwner;
    View view;
    String uid;
    String flataddress1, flatno1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, rent1, deposit1, floor1, furnished1, maintance1, parking1, tenant1, state1;
    String str;
    flatDetails fd;
    public FragmentFlatOwner() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment__flat__owner,container,false);
        {

            deletePropertybyOwner = view.findViewById(R.id.btnDeleteProperty);
            uid = ((owner_after_clicking_hisproperty)getActivity()).getStrouid();
            ref = FirebaseDatabase.getInstance().getReference().child("owners").child(uid).child("fd");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    floor1=dataSnapshot.child("floor").getValue(Long.class).toString();
                    if(dataSnapshot.child("furnished").getValue().toString().equals("true"))
                    {
                        furnished1="Furnished";
                    }
                    else
                    {
                        furnished1="Not Furnished";
                    }
                    bhk1=dataSnapshot.child("bhk").getValue(Long.class).toString();
                    if(dataSnapshot.child("parking").getValue().toString().equals("true"))
                    {
                        parking1="Available";
                    }

                    else
                    {
                        parking1="Not available";
                    }
                    if(dataSnapshot.child("lift").getValue().toString().equals("true"))
                    {
                        lift1="available";
                    }
                    else
                    {
                        lift1="Not available";
                    }
                    tenant1=dataSnapshot.child("tenant").getValue(String.class);
                    ((TextView)view.findViewById(R.id.floor)).setText(floor1);
                    ((TextView)view.findViewById(R.id.furnished)).setText(furnished1);
                    ((TextView)view.findViewById(R.id.bhk)).setText(bhk1);
                    ((TextView)view.findViewById(R.id.parking)).setText(parking1);
                    ((TextView)view.findViewById(R.id.lift)).setText(lift1);
                    ((TextView)view.findViewById(R.id.tenant)).setText(tenant1);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            deletePropertybyOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                   final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    ref=database.getReference().child("owners").child(uid);
                    ref.child("fd").child("apartmentname").setValue("");
                    ref.child("fd").child("apartmentno").setValue(-1);
                    ref.child("fd").child("area").setValue("");
                    ref.child("fd").child("bhk").setValue(-1);
                    ref.child("fd").child("city").setValue("");
                    ref.child("fd").child("deposit").setValue(-1);
                    ref.child("fd").child("flag").setValue(false);
                    ref.child("fd").child("flatno").setValue(-1);
                    ref.child("fd").child("floor").setValue(-1);
                    ref.child("fd").child("furnished").setValue(false);
                    ref.child("fd").child("landmark").setValue("");
                    ref.child("fd").child("lift").setValue(false);
                    ref.child("fd").child("maintainence").setValue(-1);
                    ref.child("fd").child("parking").setValue(false);
                    ref.child("fd").child("rent").setValue(-1);
                    ref.child("fd").child("state").setValue("");
                    ref.child("fd").child("tenant").setValue("");

                    ref1=database.getReference().child("owners").child(uid).child("nf");
                    ref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot p : dataSnapshot.getChildren())
                            {
                                p.child("Status").getRef().setValue("Delete");
                                s1=p.child("tuid").getValue().toString();
                                ref2 = database.getReference().child("tenants").child(s1).child("nf").child(uid);
                                ref2.child("Status").getRef().setValue("Property Deleted by Owner");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //startActivity(new Intent(getApplicationContext(), ownerdashboardfragment.class));

                }
            });


        }
        return view;
    }

}