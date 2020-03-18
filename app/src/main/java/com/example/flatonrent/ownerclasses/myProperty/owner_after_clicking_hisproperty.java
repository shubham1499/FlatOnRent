package com.example.flatonrent.ownerclasses.myProperty;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.notification.ownernotification_fragment;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.FragmentAddressOwner;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.FragmentFlatOwner;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.FragmentRentOwner;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.ViewPagerAdapterOwner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class owner_after_clicking_hisproperty extends AppCompatActivity {
    DatabaseReference ref,ref1,ref2;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ImageView houseImage;
    String flataddress1, flatno1,s1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, rent1, deposit1, floor1, furnished1, maintance1, parking1, tenant1, state1;
    String str,date,time;
    flatDetails fd;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    String strouid;
    String key="";
    String imageUrl="";
    Button deletePropertybyOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_after_clicking_hisproperty);
        deletePropertybyOwner = findViewById(R.id.btnDeleteProperty);
        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        // ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding Fragments

        //adapter Setup

        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {

            str = mBundle.getString("Image");
            strouid = str;

            FragmentManager manager = getSupportFragmentManager();
            final ViewPagerAdapterOwner adapter=new ViewPagerAdapterOwner(manager);
            adapter.AddFragment(new FragmentFlatOwner(), "Flat");
            adapter.AddFragment(new FragmentRentOwner(), "Rent");
            adapter.AddFragment(new FragmentAddressOwner(), "Address");

            viewPager.setAdapter(adapter);
            tablayout.setupWithViewPager(viewPager);
            houseImage = (ImageView) findViewById(R.id.flatimage);


            // houseImage.setImageResource(mBundle.getInt("Description"));

//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    fd=dataSnapshot.getValue(flatDetails.class);
//                    apartment1=dataSnapshot.child("apartmentname").getValue(String.class);
//                    apartmentno1=(fd.getApartmentno())+"";
//                    Log.i("apartmentno1",apartmentno1);
//                    bhk1=(fd.getBHK())+"";
//                    rent1=(fd.getRent())+"";
//                    deposit1=(fd.getDeposit())+"";
//                    floor1=(fd.getFloor())+"";
//                    if(dataSnapshot.child("furnished").getValue().toString().equals("true"))
//                    {
//                        furnished1="Furnished";
//                    }
//                    else
//                    {
//                        furnished1="Not Furnished";
//                    }
//                    maintance1=dataSnapshot.child("maintainence").getValue(Long.class).toString();
//                    if(dataSnapshot.child("parking").getValue().toString().equals("true"))
//                    {
//                        parking1="Available";
//                    }
//                    else
//                    {
//                        parking1="Not available";
//                    }
//                    flataddress1=dataSnapshot.child("flataddress").getValue(String.class);
//                    if(dataSnapshot.child("lift").getValue().toString().equals("true"))
//                    {
//                       lift1="available";
//                    }
//                    else
//                    {
//                      lift1="Not available";
//                    }
//                    area1=dataSnapshot.child("area").getValue(String.class);
//                    tenant1=dataSnapshot.child("tenant").getValue(String.class);
//                    city1=dataSnapshot.child("city").getValue(String.class);
//
//                    ((TextView)findViewById(R.id.apartmentname)).setText(apartment1);
//                    ((TextView)findViewById(R.id.bhk)).setText(bhk1);
////                    ((TextView)findViewById(R.id.rent)).setText(rent1);
////                    ((TextView)findViewById(R.id.deposit)).setText(deposit1);
////                    ((TextView)findViewById(R.id.floor)).setText(floor1);
////                    ((TextView)findViewById(R.id.furnished)).setText(furnished1);
////                    ((TextView)findViewById(R.id.maintance)).setText(maintance1);
////                    ((TextView)findViewById(R.id.parking)).setText(parking1);
////                    ((TextView)findViewById(R.id.flataddress)).setText(flataddress1);
////                    ((TextView)findViewById(R.id.lift)).setText(lift1);
////                    ((TextView)findViewById(R.id.area)).setText(area1);
////                    ((TextView)findViewById(R.id.tenant)).setText(tenant1);
////                    ((TextView)findViewById(R.id.city)).setText(city1);
////                    ((TextView)findViewById(R.id.flatno)).setText(flatno1);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

//            final Button request = (Button) findViewById(R.id.request);
//            eText=(EditText) findViewById(R.id.editText1);
//            txttime=(EditText) findViewById(R.id.time);
//
//            eText.setInputType(InputType.TYPE_NULL);
//            eText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Calendar cldr = Calendar.getInstance();
//                    int day = cldr.get(Calendar.DAY_OF_MONTH);
//                    int month = cldr.get(Calendar.MONTH);
//                    int year = cldr.get(Calendar.YEAR);
//                    // date picker dialog
//                    picker = new DatePickerDialog(owner_after_clicking_hisproperty.this,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                    eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    date=(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//
//                                }
//                            }, year, month, day);
//                    picker.show();
//                }
//            });


//            request.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mAuth = FirebaseAuth.getInstance();
//                    database = FirebaseDatabase.getInstance();
//
//                    time=txttime.getText()+"am";
//                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                    ownernotification_fragment Ofragment = new ownernotification_fragment();
//                    ref=database.getReference().child("tenants").child(uid);
//                    ref.child("nf").child(str).child("Time").setValue(time);
//                    ref.child("nf").child(str).child("Date").setValue(date);
//                    ref.child("nf").child(str).child("Status").setValue("pending");
//                    ref.child("nf").child(str).child("ouid").setValue(str);
//
//                    Bundle args = new Bundle();
//                    args.putString("tuid", uid);
//                    args.putString("ouid", str);
//                    args.putString("date",date);
//                    args.putString("time",time);
//                    Ofragment.setArguments(args);
//                    FragmentManager manager = getSupportFragmentManager();
//                    FragmentTransaction t = manager.beginTransaction();
//                    t.add(R.id.Linearlayoutfinal, Ofragment);
//                    t.commit();
//                    request.setText("Requested!!!");
//
//                    Toast.makeText(getApplicationContext(), "msg sent", Toast.LENGTH_SHORT).show();
//                    //startActivity(intent);
//                }
//            });
            final ImageView fm=findViewById(R.id.flatimageowner);

            //loadFlatImage
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            {
                Log.i("shubham","infirebaseauth");
                StorageReference iref = FirebaseStorage.getInstance().getReference().child("owners").child(str)
                        .child("flatimages").child("flatimage1");
                iref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(getApplicationContext()!=null)
                        {
                            Log.i("shubham","ingetapplicationcontext");
                            Picasso.with(getApplicationContext()).load(uri.toString()).into(fm);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

        }
    }

    public String getStrouid() {
        return strouid;
    }
}
