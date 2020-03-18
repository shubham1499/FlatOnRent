package com.example.flatonrent.ownerclasses.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.main_home_owner;
import com.example.flatonrent.ownerclasses.ownerDataFields.ownerRequests;
import com.example.flatonrent.ownerclasses.postingNewProperty.postNewProperty3;
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

public class owner_notification_afterclicking_tenant extends AppCompatActivity {

    String ouid;
    DatabaseReference ref;
    DatabaseReference tempref;
    String tenantuid;
    SelectableRoundedImageView tenantprofilepichere;
    StorageReference stref;
    Button btnConfirmApp,btnCancelApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_notification_afterclicking_tenant);
        if (getIntent().getExtras() != null) {
            tenantuid = getIntent().getExtras().getString("selectedtenantuid");
        }
        ouid = FirebaseAuth.getInstance().getUid();

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tenantprofilepichere=findViewById(R.id.tenantprofilepic);
        btnConfirmApp = findViewById(R.id.btnConfirmAppointment);
        btnCancelApp  = findViewById(R.id.btnCancelAppointment);


        //for tenant personal details
        ref = FirebaseDatabase.getInstance().getReference().child("tenants").child(tenantuid).child("pd");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cityhere = dataSnapshot.child("city").getValue(String.class);
                String firstnamehere = dataSnapshot.child("firstname").getValue(String.class);
                String lastnamehere = dataSnapshot.child("lastname").getValue(String.class);
                String emailidhere = dataSnapshot.child("emailid").getValue(String.class);
                String phonenumberhere = dataSnapshot.child("phonenumber").getValue(String.class);

                ((EditText) (findViewById(R.id.oCity))).setText(cityhere);
                ((EditText) (findViewById(R.id.oFirstName))).setText(firstnamehere);
                ((EditText) (findViewById(R.id.oLastName))).setText(lastnamehere);
                ((EditText) (findViewById(R.id.oPhoneNumber))).setText(phonenumberhere + "");
                ((EditText) (findViewById(R.id.oEmail))).setText(emailidhere);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //for date and time
        tempref = FirebaseDatabase.getInstance().getReference().child("owners").child(ouid).child("nf").child(tenantuid);
        tempref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerRequests tempor = dataSnapshot.getValue(ownerRequests.class);
                ((EditText) findViewById(R.id.oDate)).setText(tempor.getDate());
                ((EditText) findViewById(R.id.oTime)).setText(tempor.getTime());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        loadTenantImageInfo();

        btnConfirmApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(owner_notification_afterclicking_tenant.this);
                dialog.setContentView(R.layout.dialogbox_owner_confirmapp);

                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("CONFIRM APPOINTMENT?");

                Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

                //IF PRESSED OK then update status as "CONFIRMED"
                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("owners").child(ouid)
                                                    .child("nf").child(tenantuid);
                        temp.child("Status").setValue("Confirmed");


                        temp= FirebaseDatabase.getInstance().getReference().child("tenants").child(tenantuid)
                                .child("nf").child(ouid);
                        temp.child("Status").setValue("Confirmed");


                        dialog.dismiss();

                        Intent i=new Intent(owner_notification_afterclicking_tenant.this, main_home_owner.class);
                        i.putExtra("loggedas","owners");
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        finish();
                    }
                });

                //IF PRESSED CANCEL do nothing ..keep the status as "PENDING"
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnCancelApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(owner_notification_afterclicking_tenant.this);
                dialog.setContentView(R.layout.dialogbox_owner_confirmapp);


                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("CANCEL APPOINTMENT?");

                Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);


                //IF PRESSED OK the update status as "CANCELLED"
                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("owners").child(ouid)
                                .child("nf").child(tenantuid);
                        temp.child("Status").setValue("Cancelled");


                        temp= FirebaseDatabase.getInstance().getReference().child("tenants").child(tenantuid)
                                .child("nf").child(ouid);
                        temp.child("Status").setValue("Cancelled");


                        Intent i=new Intent(owner_notification_afterclicking_tenant.this, main_home_owner.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("loggedas","owners");
                        startActivity(i);
                        finish();

                        dialog.dismiss();
                    }
                });


                //IF PRESSED CANCEL then do nothing..keep the status as "PENDING"
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
    void loadTenantImageInfo()
    {
        //for profile image
        stref= FirebaseStorage.getInstance().getReference().child("tenants").child(tenantuid).child("profilepic").child("myPhoto.jpg");
        stref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(getApplicationContext()!=null) {
                    Picasso.with(getApplicationContext()).load(uri.toString()).into(tenantprofilepichere);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(owner_notification_afterclicking_tenant.this,"No image found for Tenant",Toast.LENGTH_SHORT).show();
            }
        });
    }
}