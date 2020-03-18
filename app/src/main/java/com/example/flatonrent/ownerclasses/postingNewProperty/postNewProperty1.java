package com.example.flatonrent.ownerclasses.postingNewProperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.firebase.auth.FirebaseAuth;

public class postNewProperty1 extends AppCompatActivity {
CheckBox furniture1,furniture2,lift1,lift2,parking1,parking2;
Button btnContinue2;
EditText bhk;

boolean flag1=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_property1);
        furniture1=findViewById(R.id.furnitureIdYes);
        furniture2=findViewById(R.id.furnitureIdNo);
        lift1=findViewById(R.id.liftIdYes);
        lift2=findViewById(R.id.liftIdNo);
        parking1=findViewById(R.id.parkingIdYes);
        parking2=findViewById(R.id.parkingIdNo);
        btnContinue2=findViewById(R.id.btnContinue2);

        bhk=findViewById(R.id.txtbhk);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        furniture1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    furniture2.setChecked(false);
                    ((TextView)findViewById(R.id.txtvfurnished)).setError(null);
                }
            }
        });
        furniture2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    furniture1.setChecked(false);
                    ((TextView)findViewById(R.id.txtvfurnished)).setError(null);
                }
            }
        });

        lift1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    lift2.setChecked(false);
                    ((TextView)findViewById(R.id.txtvlift)).setError(null);
                }
            }
        });
        lift2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lift1.setChecked(false);
                    ((TextView)findViewById(R.id.txtvlift)).setError(null);
                }
            }
        });

        parking1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                  parking2.setChecked(false);
                    ((TextView)findViewById(R.id.txtvparking)).setError(null);
                }
            }
        });
        parking2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    parking1.setChecked(false);
                    ((TextView)findViewById(R.id.txtvparking)).setError(null);
                }
            }
        });

        btnContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1=true;
                if(bhk.getText().toString().trim().equalsIgnoreCase(""))
                {
                    bhk.setError("Enter BHK");
                    flag1=false;
                }
                if(!(furniture1.isChecked()) && !(furniture2.isChecked()))
                {
                    ((TextView)findViewById(R.id.txtvfurnished)).requestFocus();
                    ((TextView)findViewById(R.id.txtvfurnished)).setError("");
                    flag1=false;
                }
                if(!(lift1.isChecked()) && !(lift2.isChecked()))
                {
                    ((TextView)findViewById(R.id.txtvlift)).requestFocus();
                    ((TextView)findViewById(R.id.txtvlift)).setError("");
                    flag1=false;
                }
                if(!(parking1.isChecked()) && !(parking2.isChecked()))
                {
                    ((TextView)findViewById(R.id.txtvparking)).requestFocus();
                    ((TextView)findViewById(R.id.txtvparking)).setError("");
                    flag1=false;
                }


                if(flag1) {
                    int bhk1=Integer.parseInt(bhk.getText().toString().trim());
                    Boolean furnished=(furniture1.isChecked())?true:false;
                    Boolean parking=(parking1.isChecked())?true:false;
                    Boolean lift=(lift1.isChecked())?true:false;
                    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    flatDetails fd=new flatDetails(bhk1,furnished,parking,lift,uid);

                    Intent intent = new Intent(getApplicationContext(), postNewProperty2.class);
                    intent.putExtra("flatdetails1",fd);
                    startActivity(intent);
                }
            }
        });

    }
}
