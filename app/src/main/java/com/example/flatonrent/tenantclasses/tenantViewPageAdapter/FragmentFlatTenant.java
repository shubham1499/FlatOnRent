package com.example.flatonrent.tenantclasses.tenantViewPageAdapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.myProperty.owner_after_clicking_hisproperty;
import com.example.flatonrent.ownerclasses.notification.ownernotification_fragment;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.tenantclasses.tenant_after_clicking_property_fromhome1.tenant_after_clicking_property_from_thome1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.SyncStateContract.Helpers.update;

public class FragmentFlatTenant extends Fragment {
    DatePickerDialog picker;
    private TimePicker timePicker1;
    static final int TIME_DIALOG_ID = 1111;
    DatabaseReference ref;
    String m,h;
    View view;
    String tuid,ouid;
    String flataddress1, flatno1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, rent1, deposit1, floor1, furnished1, maintance1, parking1, tenant1, state1;
    String str;
    String date;
    String time;
    flatDetails fd;
    Button sendReq;
    EditText datehere;
    private Spinner spinner;
    private Calendar calendar;
    private String format = "";

    public FragmentFlatTenant() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_flat_tenant,container,false);
        sendReq = (Button)view.findViewById(R.id.btnSendRequestToOwner);
        spinner = view.findViewById(R.id.spinner2);
        datehere = view.findViewById(R.id.date);
            tuid = FirebaseAuth.getInstance().getUid();
            ouid = ((tenant_after_clicking_property_from_thome1)getActivity()).getStrouid();

           final List<String>timings=new ArrayList<>();
            timings.add("Choose Time");
            timings.add("8am");
            timings.add("9am");
            timings.add("10am");
            timings.add("11am");
            timings.add("12pm");
            timings.add("1pm");
            timings.add("2pm");
            timings.add("3pm");
            timings.add("4pm");
            timings.add("5pm");
            timings.add("6pm");

            ArrayAdapter<String>dataAdapter=new ArrayAdapter(getContext(), R.layout.spinner_item,timings);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);



            ref = FirebaseDatabase.getInstance().getReference().child("owners").child(ouid).child("fd");
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

        datehere.setInputType(InputType.TYPE_NULL);
        datehere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datehere.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                date=(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                picker.show();
            }
        });



        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time=spinner.getSelectedItem().toString().trim();

                boolean flag=true;
                if(time.equals("Choose Time"))
                {
                    Toast.makeText(getContext(),"Choose Time",Toast.LENGTH_SHORT).show();
                    flag=false;
                    return;
                }
                if(date==null)
                {
                    Toast.makeText(getContext(),"Choose Date",Toast.LENGTH_SHORT).show();
                    flag=false;
                    return;
                }
                if(flag) {
                    ref = FirebaseDatabase.getInstance().getReference().child("tenants").child(tuid);
                    ref.child("nf").child(ouid).child("Time").setValue(time);
                    ref.child("nf").child(ouid).child("Date").setValue(date);
                    ref.child("nf").child(ouid).child("Status").setValue("pending");
                    ref.child("nf").child(ouid).child("ouid").setValue(ouid);


                    ref = FirebaseDatabase.getInstance().getReference().child("owners").child(ouid);
                    ref.child("nf").child(tuid).child("Time").setValue(time);
                    ref.child("nf").child(tuid).child("Date").setValue(date);
                    ref.child("nf").child(tuid).child("Status").setValue("pending");
                    ref.child("nf").child(tuid).child("tuid").setValue(tuid);
                    Toast.makeText(getContext(), "msg sent", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                }
            }
        });

        return view;
    }





}

