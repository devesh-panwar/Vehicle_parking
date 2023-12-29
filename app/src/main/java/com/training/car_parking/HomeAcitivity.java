package com.training.car_parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.training.car_parking.database.Car;
import com.training.car_parking.database.MyDatabase;

public class HomeAcitivity extends AppCompatActivity {

    MyDatabase myDatabase;
    Button entry,register;
    EditText ow,veh,ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);
            entry=findViewById(R.id.button);
            register=findViewById(R.id.button2);
            ow=findViewById(R.id.owner);
            veh=findViewById(R.id.vehcical_number);
            ph=findViewById(R.id.phone_number);
            myDatabase=new MyDatabase(this);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String owne=ow.getText().toString();
                    String vehi=veh.getText().toString();
                    String pho=ph.getText().toString();
                    if(!(owne.isEmpty() && vehi.isEmpty() && pho.isEmpty())) {
                        if(myDatabase.checkIfExists(owne,vehi)) {
                            Toast.makeText(HomeAcitivity.this, "Already exists PLEASE VISIT TO ENTRY SECTION", Toast.LENGTH_SHORT).show();
                        }
                    else{
                        Car car = new Car(owne, vehi, pho);
                        long result = myDatabase.insertRecored(car);
                        if (result > 0)
                            Toast.makeText(HomeAcitivity.this, "Data inserted succefully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(HomeAcitivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }}
                    else {
                        Toast.makeText(HomeAcitivity.this, "Length is To small", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String owne=ow.getText().toString();
                    String vehi=veh.getText().toString();
                    String phon=ph.getText().toString();
//                    if(!(owne.isEmpty() && vehi.isEmpty()))
//                        Toast.makeText(HomeAcitivity.this, "length is too short", Toast.LENGTH_SHORT).show();
                    if(myDatabase.checkIfExists(owne,vehi)){
                        Intent i=new Intent(HomeAcitivity.this,ParkingActivity.class);
                        i.putExtra("name",owne);
                        i.putExtra("vehi",vehi);
                        i.putExtra("phone",phon);
                        startActivity(i);
                    }
                   else Toast.makeText(HomeAcitivity.this, "Please Do Entry Before Entry", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.item1){
            Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.admin_login);


            Button btn=dialog.findViewById(R.id.dial_buton);
            EditText e1,e2;
            dialog.show();
            e1=dialog.findViewById(R.id.dialgon_user);
            e2=dialog.findViewById(R.id.dialog_pass);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    String user=e1.getText().toString();
                    String pass=e2.getText().toString();
                    if(user.equals("anwik") && pass.equals("123")){
                        Toast.makeText(HomeAcitivity.this, "welcom admin", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(HomeAcitivity.this, Admin_Activity.class);
                        startActivity(i);
                    } else Toast.makeText(HomeAcitivity.this, "please put somthing", Toast.LENGTH_SHORT).show();
                }
            });


        }
        return super.onOptionsItemSelected(item);
    }
}