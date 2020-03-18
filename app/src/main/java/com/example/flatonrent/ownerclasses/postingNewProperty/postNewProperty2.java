package com.example.flatonrent.ownerclasses.postingNewProperty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;

public class postNewProperty2 extends AppCompatActivity {

boolean flag1=false;
CheckBox bachelor,family,students;
EditText txtrent1,txtdeposit1,txtmaintainence1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_property2);

       final flatDetails fd=getIntent().getExtras().getParcelable("flatdetails1");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        bachelor=findViewById(R.id.bachelorsId);
        family=findViewById(R.id.familyId);
        students=findViewById(R.id.studentsId);

        txtrent1=findViewById(R.id.txtrent);
        txtdeposit1=findViewById(R.id.txtdeposit);
        txtmaintainence1=findViewById(R.id.txtmaintainence);


        bachelor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((TextView)findViewById(R.id.txtvtenant)).setError(null);

            }
        });

        students.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((TextView)findViewById(R.id.txtvtenant)).setError(null);

            }
        });

        family.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((TextView)findViewById(R.id.txtvtenant)).setError(null);

            }
        });
        findViewById(R.id.btnContinue2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flag1=true;

                if(txtrent1.getText().toString().trim().equalsIgnoreCase(""))
                {
                    txtrent1.setError("Enter Rent Amount");
                    flag1=false;
                }
                if(txtdeposit1.getText().toString().trim().equalsIgnoreCase(""))
                {
                    txtdeposit1.setError("Enter Deposit Amount");
                    flag1=false;
                }
                if(txtmaintainence1.getText().toString().trim().equalsIgnoreCase(""))
                {
                    txtmaintainence1.setError("Enter Maintainence Charge");
                    flag1=false;
                }

                if(!(bachelor.isChecked()) && !(family.isChecked()) && !(students.isChecked()))
                {
                    ((TextView)findViewById(R.id.txtvtenant)).requestFocus();
                    ((TextView)findViewById(R.id.txtvtenant)).setError(" ");
                    flag1=false;
                }

                if(flag1) {
                    String tenant1="";
                    if(bachelor.isChecked())
                    {
                        tenant1="Bachleors";
                    }
                    if(family.isChecked())
                    {
                        tenant1=tenant1+"/"+"Family";
                    }
                    if(students.isChecked())
                    {
                        tenant1=tenant1+"/"+"Students";
                    }
                    int rent1=Integer.parseInt(((EditText)findViewById(R.id.txtrent)).getText().toString());
                    int deposit1=Integer.parseInt(((EditText)findViewById(R.id.txtdeposit)).getText().toString());
                    int maintainence1=Integer.parseInt(((EditText)findViewById(R.id.txtdeposit)).getText().toString());
                    fd.setRent(rent1);
                    fd.setDeposit(deposit1);
                    fd.setMaintainence(maintainence1);
                    fd.setTenant(tenant1);

                    Intent intent = new Intent(getApplicationContext(), postNewProperty3.class);
                    intent.putExtra("flatdetails2",fd);
                    startActivity(intent);
                }
            }
        });
    }
}
