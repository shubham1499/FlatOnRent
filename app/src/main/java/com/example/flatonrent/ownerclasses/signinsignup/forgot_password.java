package com.example.flatonrent.ownerclasses.signinsignup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flatonrent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {

    private EditText emailenter;
    private Button resetbtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailenter = (EditText)findViewById(R.id.forgetpasstv);
        resetbtn = (Button) findViewById(R.id.resetpassbtn);

        firebaseAuth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailentered = emailenter.getText().toString().trim();

                if(emailentered.equals(""))
                {
                    Toast.makeText(forgot_password.this,"Please enter Email",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    firebaseAuth.sendPasswordResetEmail(emailentered).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgot_password.this,"Email Sent Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgot_password.this,loginPage.class));
                            }
                            else
                            {
                                Toast.makeText(forgot_password.this,"Email Not Sent Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}