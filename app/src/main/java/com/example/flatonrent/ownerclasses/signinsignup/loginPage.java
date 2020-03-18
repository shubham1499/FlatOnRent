package com.example.flatonrent.ownerclasses.signinsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.main_home_owner;
import com.example.flatonrent.tenantclasses.main_home_tenant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginPage extends AppCompatActivity {
    private TextView forgotpass;
    private String email, password, emailPattern;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private String uid;
    Boolean flag1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        forgotpass  = findViewById(R.id.tvForgotPass);
        ((EditText) findViewById(R.id.txtemail)).setText("");
        ((EditText) findViewById(R.id.txtpassword)).setText("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),forgot_password.class));
            }
        });
    }

    public void gotoRegisterPage(View view) {
        startActivity(new Intent(getApplicationContext(), registerPage.class));
    }

    public void gotoHomePage(View view) {

        flag1 = true;
        ref = FirebaseDatabase.getInstance().getReference();
        email = (((EditText) findViewById(R.id.txtemail)).getText()).toString().trim();
        password = (((EditText) findViewById(R.id.txtpassword)).getText()).toString().trim();

        if (email.equalsIgnoreCase("")) {
            ((EditText) findViewById(R.id.txtemail)).setError("Enter Email Address");
            flag1 = false;
        } else if (!email.matches(emailPattern)) {
            ((EditText) findViewById(R.id.txtemail)).setError("Enter Valid Email Address");
            flag1 = false;
        }

        if (password.equalsIgnoreCase("")) {
            ((EditText) findViewById(R.id.txtpassword)).setError("Enter Password");
            flag1 = false;
        } else if (password.length() < 6) {
            ((EditText) findViewById(R.id.txtpassword)).setError("Password should be of minimum 6 characters");
            flag1 = false;
        }

        if (flag1) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //Always get uid from within the listner
                            uid = mAuth.getCurrentUser().getUid();

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot p : dataSnapshot.getChildren()) {
                                            Log.i("p.getkey()", p.getKey());
                                            for (DataSnapshot q : p.getChildren()) {
                                                if (uid.equals(q.getKey())) {
                                                    Log.i("q.getvalue()", q.getKey());


                                                    Intent i;
                                                    if (p.getKey().equals("owners")) {
                                                        i = new Intent(loginPage.this, main_home_owner.class);
                                                        i.putExtra("loggedas", "owners");
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        Toast.makeText(getApplicationContext(), "owners", Toast.LENGTH_SHORT).show();
                                                        Log.i("isssemail", email);
                                                        Log.i("isssowners", uid);
                                                        ((EditText) findViewById(R.id.txtemail)).setText("");
                                                        ((EditText) findViewById(R.id.txtpassword)).setText("");
                                                        startActivity(i);
                                                        finish();
                                                    } else if (p.getKey().equals("tenants")) {
                                                        i = new Intent(loginPage.this, main_home_tenant.class);
                                                        i.putExtra("loggedas", "tenants");
                                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        Toast.makeText(loginPage.this, "tenants", Toast.LENGTH_SHORT).show();
                                                        Log.i("isssemail", email);
                                                        Log.i("issstenants", uid);
                                                        ((EditText) findViewById(R.id.txtemail)).setText("");
                                                        ((EditText) findViewById(R.id.txtpassword)).setText("");
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            }
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                               /* ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Intent i;
                                        if (dataSnapshot.child("owners").hasChild(uid)) {
                                            i = new Intent(loginPage.this, main_home_owner.class);
                                            i.putExtra("loggedas", "owners");
                                            Toast.makeText(getApplicationContext(), "owners", Toast.LENGTH_SHORT).show();
                                            Log.i("isssemail", email);
                                            Log.i("isssowners", uid);
                                            startActivity(i);
                                            //finish();
                                        }
                                        else if (dataSnapshot.child("tenants").hasChild(uid)) {
                                            i = new Intent(loginPage.this, main_home_tenant.class);
                                            i.putExtra("loggedas", "tenants");
                                            Toast.makeText(loginPage.this, "tenants", Toast.LENGTH_SHORT).show();
                                            Log.i("isssemail", email);
                                            Log.i("issstenants", uid);
                                            startActivity(i);
                                            //finish();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }*/
                                }
                                 else{
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
