package com.example.flatonrent.ownerclasses.postingNewProperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.main_home_owner;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class postNewProperty3 extends AppCompatActivity {
EditText flatno,floor,aptname,aptno,area,city,state,landmark;
Button btnContinue3;
ImageView uploadflatimage;
TextView tvflatiimagehere;
private FirebaseAuth mAuth;
private DatabaseReference ref;
private final int PICK_IMAGE_REQUEST = 71;
private Uri filePath;
String uid;
Boolean flag1;
ProgressDialog progressDialog;
FirebaseStorage storage;
StorageReference storageReference;
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_property3);

        final flatDetails fdd=getIntent().getExtras().getParcelable("flatdetails2");
        mAuth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        flatno=findViewById(R.id.txtflatno);
        floor=findViewById(R.id.txtfloor);
        aptname=findViewById(R.id.txtaptname);
        aptno=findViewById(R.id.txtaptno);
        area=findViewById(R.id.txtarea);
        city=findViewById(R.id.txtcity);
        state=findViewById(R.id.txtstate);
        landmark=findViewById(R.id.txtLandmark);
        btnContinue3=findViewById(R.id.btnContinue3);
        uploadflatimage=findViewById(R.id.imageofflat);
        tvflatiimagehere = findViewById(R.id.tvflatimage);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        btnContinue3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag1 = true;
                tvflatiimagehere.setError(null);
                if (flatno.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    flatno.setError("Enter FlatNo");
                }
                if (floor.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    floor.setError("Enter Floor");
                }
                if (aptname.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    aptname.setError("Enter Apartment Name");
                }
                if (aptno.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    aptno.setError("Enter Apartment Number");
                }
                if (area.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    area.setError("Enter Locality");
                }
                if (city.getText().toString().trim().equalsIgnoreCase("")) {
                    flag1 = false;
                    city.setError("Enter City");
                }
                if(state.getText().toString().trim().equalsIgnoreCase(""))
                {
                    flag1=false;
                    state.setError("Enter State");
                }
                if(landmark.getText().toString().trim().equalsIgnoreCase(""))
                {
                    flag1=false;
                    landmark.setError("Enter Landmark");
                }
                if(uploadflatimage.getDrawable()==null)
                {
                    flag1=false;
                    tvflatiimagehere.setError("Select Flat Images");
                }
                else
                {
                    tvflatiimagehere.setError(null);
                }
                if (flag1) {

                    int flatno1 = Integer.parseInt(flatno.getText().toString());
                    int floor1 = Integer.parseInt(floor.getText().toString());
                    String aptname1 = aptname.getText().toString();
                    int aptno1 = Integer.parseInt(aptno.getText().toString());
                    String area1 = area.getText().toString();
                    String city1 = city.getText().toString();
                    String state1 = state.getText().toString();
                    String landmark1 = landmark.getText().toString();

                    fdd.setFlatno(flatno1);
                    fdd.setFloor(floor1);
                    fdd.setApartmentname(aptname1);
                    fdd.setApartmentno(aptno1);
                    fdd.setArea(area1);
                    fdd.setCity(city1);
                    fdd.setState(state1);
                    fdd.setLandmark(landmark1);
                    fdd.setFlag(true);

                    //uploading flatimage in firestorage
                    uploadImageInFirestorage();


                    //uploading flat details in firebase realtime database
                    uploadInFirebase(fdd);

                    Intent i = new Intent(postNewProperty3.this, main_home_owner.class);
                    i.putExtra("loggedas", "owners");
                    startActivity(i);

                    finish();
                }
            }
        });
        uploadflatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    public void uploadInFirebase(flatDetails fdd)
    {
        uid=mAuth.getCurrentUser().getUid();
        ref=FirebaseDatabase.getInstance().getReference().child("owners").child(uid);
        ref.child("fd").setValue(fdd);
        Toast.makeText(getApplicationContext(),"Posted",Toast.LENGTH_SHORT).show();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadflatimage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImageInFirestorage() {
    String uid1=mAuth.getCurrentUser().getUid();
        if(filePath != null)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String pathofimage="owners"+"/"+uid1+"/"+"flatimages/";
            StorageReference ref = storageReference.child(pathofimage+ "flatimage1");

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
        progressDialog.dismiss();
    }
}