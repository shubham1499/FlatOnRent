package com.example.flatonrent.ownerclasses.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.main_home_owner;
import com.example.flatonrent.ownerclasses.signinsignup.loginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;


import static android.app.Activity.RESULT_OK;

public class ownerprofile_fragment extends Fragment {
    private static final int CHOOSE_IMAGE = 101;
    Uri uriprofileImage;
    SelectableRoundedImageView profilepic;
    ProgressBar progressbar1;
    String profileImageUrl,uid;
    FirebaseAuth mAuth;
    DatabaseReference ref1,ref;
    Button btnOwnerLogout;
    boolean flag1=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root=inflater.inflate(R.layout.owner_profile_fragment_layout, null);
        btnOwnerLogout = root.findViewById(R.id.btnOwnerLogout);
        mAuth = FirebaseAuth.getInstance();
        uid=mAuth.getUid();

        progressbar1=root.findViewById(R.id.progressbar);
        profilepic=((SelectableRoundedImageView)root.findViewById(R.id.imageprofilepic));


        Log.i("newpic:",profilepic.getTag().toString());
        if(profilepic.getDrawable()==getResources().getDrawable(R.drawable.photo))
        {
            Log.i("In If","In if");
            profilepic.setTag(0);
        }
        else
        {
            Log.i("In If","In if123");
            profilepic.setTag(1);
        }
        ref= FirebaseDatabase.getInstance().getReference("owners");

        ref=ref.child(uid).child("pd");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String cityhere=dataSnapshot.child("city").getValue(String.class);
                String firstnamehere=dataSnapshot.child("firstname").getValue(String.class);
                String lastnamehere=dataSnapshot.child("lastname").getValue(String.class);
                String emailidhere=dataSnapshot.child("emailid").getValue(String.class);
                String phonenumberhere=dataSnapshot.child("phonenumber").getValue(String.class);

                ((EditText)(root.findViewById(R.id.oCity))).setText(cityhere);
                ((EditText)(root.findViewById(R.id.oFirstName))).setText(firstnamehere);
                ((EditText)(root.findViewById(R.id.oLastName))).setText(lastnamehere);
                ((EditText)(root.findViewById(R.id.oPhoneNumber))).setText(phonenumberhere+"");
                ((EditText)(root.findViewById(R.id.oEmail))).setText(emailidhere);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity().getApplicationContext(),"Some Error Occurred",Toast.LENGTH_SHORT).show();
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        loadOwnerImageInformation();

        root.findViewById(R.id.btnSaveOwnerInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1=true;
                if(((EditText)root.findViewById(R.id.oFirstName)).getText().toString().trim().equalsIgnoreCase(""))
                {
                    ((EditText)root.findViewById(R.id.oFirstName)).requestFocus();
                    ((EditText)root.findViewById(R.id.oFirstName)).setError("Enter First Name");
                    flag1=false;
                }
                if(((EditText)root.findViewById(R.id.oLastName)).getText().toString().trim().equalsIgnoreCase(""))
                {
                    ((EditText)root.findViewById(R.id.oLastName)).requestFocus();
                    ((EditText)root.findViewById(R.id.oLastName)).setError("Enter Last Name");
                    flag1=false;
                }
                if(((EditText)root.findViewById(R.id.oPhoneNumber)).getText().toString().trim().equalsIgnoreCase(""))
                {
                    ((EditText)root.findViewById(R.id.oPhoneNumber)).requestFocus();
                    ((EditText)root.findViewById(R.id.oPhoneNumber)).setError("Enter Phone Number");
                    flag1=false;
                }
                else if(((EditText)root.findViewById(R.id.oPhoneNumber)).getText().toString().length()<10 || ((EditText)root.findViewById(R.id.oPhoneNumber)).getText().toString().length()>10)
                {
                    ((EditText)root.findViewById(R.id.oPhoneNumber)).setError("Phone Number must be 10 digits");
                    flag1=false;
                }

                if(((EditText)root.findViewById(R.id.oCity)).getText().toString().trim().equalsIgnoreCase(""))
                {
                    ((EditText)root.findViewById(R.id.oCity)).requestFocus();
                    ((EditText)root.findViewById(R.id.oCity)).setError("Enter City");
                    flag1=false;
                }
                if((profilepic.getTag().toString()).equals("0"))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Please select Profile Image",Toast.LENGTH_SHORT).show();
                    flag1=false;
                }
                if(flag1) {
                    ref1=FirebaseDatabase.getInstance().getReference().child("owners").child(uid).child("pd");

                    ref1.child("firstname").setValue(((EditText)root.findViewById(R.id.oFirstName)).getText().toString().trim());
                    ref1.child("lastname").setValue(((EditText)root.findViewById(R.id.oLastName)).getText().toString().trim());
                    ref1.child("phonenumber").setValue(((EditText)root.findViewById(R.id.oPhoneNumber)).getText().toString().trim());
                    ref1.child("city").setValue(((EditText)root.findViewById(R.id.oCity)).getText().toString().trim());
                    ref1.child("flag").setValue(true);
                    saveOwnerInfo();
                    if(getActivity()!=null)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Info Updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });


        btnOwnerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), loginPage.class));
            }
        });

        return root;
    }

    private void loadOwnerImageInformation() {
       if(mAuth.getCurrentUser()!=null)
       {
           StorageReference ref12=FirebaseStorage.getInstance().getReference().child("owners").child(uid).child("profilepic").child("myPhoto.jpg");
           ref12.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override

               public void onSuccess(Uri uri) {
                   Log.i("URI:",uri.toString());

                   //VERY IMPORTANT
                   if(getActivity()!=null) {
                       Picasso.with(getActivity().getApplicationContext()).load(uri.toString()).into(profilepic);
                   }
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   if(getActivity()!=null)
                   {
                        //Toast.makeText(getActivity().getApplicationContext(),"Could not Load Image",Toast.LENGTH_SHORT).show();
                   }
               }
           });
       }
    }

    public void saveOwnerInfo()
    {
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null && profileImageUrl!=null)
        {
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName("some display name")
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        //VERY IMPORTANT

                        if(getActivity()!=null) {
                            Toast.makeText(getActivity().getApplicationContext(), "Info Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            uriprofileImage=data.getData();
            try {
                if(getActivity()!=null)
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uriprofileImage);
                    profilepic.setImageBitmap(bitmap);


                    uploadImageToFirebaseStorage();
                }
            }catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebaseStorage() {

        String path="owners"+"/"+uid+"/"+"profilepic/";

        StorageReference profileimageref=
                FirebaseStorage.getInstance().getReference(path+"myPhoto.jpg");

        if(uriprofileImage!=null)
        {
            progressbar1.setVisibility(View.VISIBLE);
            profileimageref.putFile(uriprofileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressbar1.setVisibility(View.GONE);
                    profileImageUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    profilepic.setTag(1);
                    Log.i("profilepictag",profilepic.getTag()+"");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressbar1.setVisibility(View.GONE);
                    if(getActivity()!=null)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showImageChooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Profile Picture"),CHOOSE_IMAGE);
    }
}
