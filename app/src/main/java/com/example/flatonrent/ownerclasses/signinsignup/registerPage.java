package com.example.flatonrent.ownerclasses.signinsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.Owner;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.example.flatonrent.ownerclasses.ownerDataFields.people;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class registerPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //private ProgressBar progressBar;
    private DatabaseReference ref;
    private String uid;
    private Spinner spinner;
    Boolean flag1;
    String emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        spinner=findViewById(R.id.spinner2);
        mAuth = FirebaseAuth.getInstance();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        List<String>categories=new ArrayList<>();
        categories.add("Choose Category");
        categories.add("Tenant");
        categories.add("Owner");
        ArrayAdapter<String>dataAdapter=new ArrayAdapter(this,R.layout.spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void gotoLoginPage(View view)
    {
        final String email=(((EditText)findViewById(R.id.txtemail)).getText()).toString().trim();
        final String password=(((EditText)findViewById(R.id.txtpassword)).getText()).toString().trim();
        final String conpassword=((EditText)findViewById(R.id.txtconpassword)).getText().toString().trim();
        final String city1=((EditText)findViewById(R.id.txtcity)).getText().toString().trim();
        final String selectedCategory= spinner.getSelectedItem().toString().trim();
        flag1=true;

        if(selectedCategory.equals("Owner"))
        {
            ref=FirebaseDatabase.getInstance().getReference().child("owners");
        }
        else if(selectedCategory.equals("Tenant"))
        {
            ref=FirebaseDatabase.getInstance().getReference().child("tenants");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Select category.",Toast.LENGTH_SHORT).show();
            flag1=false;
        }

        if(city1.equalsIgnoreCase(""))
        {
            ((EditText) findViewById(R.id.txtcity)).setError("Enter City");
            flag1=false;
        }

        if(email.equalsIgnoreCase(""))
        {
            ((EditText) findViewById(R.id.txtemail)).setError("Enter Email Address");
            flag1=false;
        }
        else if(!isValidEmail(email))
        {
            ((EditText) findViewById(R.id.txtemail)).setError("Enter Valid Email Address");
            flag1=false;
        }

        if(password.equalsIgnoreCase(""))
        {
            ((EditText) findViewById(R.id.txtpassword)).setError("Enter Password");
            flag1=false;
        }
        else if(password.length()<6)
        {
            ((EditText) findViewById(R.id.txtpassword)).setError("Password should be of minimum 6 characters");
            flag1=false;
        }

        if(conpassword.equalsIgnoreCase(""))
        {
            ((EditText) findViewById(R.id.txtconpassword)).setError("Confirm Your Password");
            flag1=false;
        }
        else if(!(conpassword.equals(password)))
        {
            ((EditText) findViewById(R.id.txtconpassword)).setError("Password Not Matching");
            flag1=false;
        }

        if(flag1) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //progressBar.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                uid = mAuth.getCurrentUser().getUid();

                                people pd = new people(email, city1, false, uid);
                                if (selectedCategory.equals("Owner")) {
                                    flatDetails fd = new flatDetails(uid);
                                    Owner owner = new Owner(pd, fd);
                                    ref.child(uid).setValue(owner);
                                } else {
                                    ref.child(uid).child("pd").setValue(pd);
                                }
                                Toast.makeText(registerPage.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(registerPage.this, loginPage.class));
                                finish();
                            } else {
                                //progressBar.setVisibility(View.GONE);
                                Toast.makeText(registerPage.this, "Email already Registered.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}