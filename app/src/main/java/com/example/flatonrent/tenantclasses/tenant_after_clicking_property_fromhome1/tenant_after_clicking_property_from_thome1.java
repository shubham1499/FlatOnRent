package com.example.flatonrent.tenantclasses.tenant_after_clicking_property_fromhome1;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.FragmentAddressOwner;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.FragmentRentOwner;
import com.example.flatonrent.ownerclasses.ownerViewPageAdapter.ViewPagerAdapterOwner;
import com.example.flatonrent.tenantclasses.tenantViewPageAdapter.FragmentAddressTenant;
import com.example.flatonrent.tenantclasses.tenantViewPageAdapter.FragmentFlatTenant;
import com.example.flatonrent.tenantclasses.tenantViewPageAdapter.FragmentRentTenant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class tenant_after_clicking_property_from_thome1 extends AppCompatActivity {
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ImageView houseImage;
    String flataddress1, flatno1;
    String apartment1, apartmentno1;
    String lift1, city1;
    String area1, bhk1, rent1, deposit1, floor1, furnished1, maintance1, parking1, tenant1, state1;
    String str,date,time;
    flatDetails fd;
    EditText eText,txttime;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    String strouid;
    String key="";
    String imageUrl="";
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_after_clicking_property_from_thome1);

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
            final ImageView fm=findViewById(R.id.flatimagetenant);

            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            {
                Log.i("shubham","infirebaseauth");
                Log.i("owneruid",strouid);
                StorageReference iref = FirebaseStorage.getInstance().getReference().child("owners").child(strouid)
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

            FragmentManager manager = getSupportFragmentManager();
            final ViewPagerAdapterOwner adapter=new ViewPagerAdapterOwner(manager);
            adapter.AddFragment(new FragmentFlatTenant(), "Flat");
            adapter.AddFragment(new FragmentRentTenant(), "Rent");
            adapter.AddFragment(new FragmentAddressTenant(), "Address");

            viewPager.setAdapter(adapter);
            tablayout.setupWithViewPager(viewPager);
            houseImage = (ImageView) findViewById(R.id.flatimage);



        }
    }
    public String getStrouid() {
        return strouid;
    }

}
