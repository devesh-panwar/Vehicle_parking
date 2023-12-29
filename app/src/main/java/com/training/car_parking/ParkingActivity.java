package com.training.car_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.training.car_parking.database.Car;
import com.training.car_parking.database.MyDatabase;


public class ParkingActivity extends AppCompatActivity {

    TextView t_own,t_vehi,t_phon;
    Switch s1,s2;
    Spinner spin;
    Button btn;
    MyDatabase myDatabase;
    String ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        btn=findViewById(R.id.button4);
        t_own=findViewById(R.id.textView3);
        t_vehi=findViewById(R.id.textView5);
        t_phon=findViewById(R.id.textView7);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        spin=findViewById(R.id.spinner);
        myDatabase=new MyDatabase(this);
        Bundle b=getIntent().getExtras();
        String owner=b.getString("name");
        String vehical=b.getString("vehi");
        String phone=b.getString("phone");
        t_own.setText(owner);
        t_phon.setText(phone);
        t_vehi.setText(vehical);
        Car car=myDatabase.getCarData(owner,vehical);
        String [] parking=getResources().getStringArray(R.array.parking_type);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,parking);
       // s1.(car.getExtra());
        spin.setAdapter(adapter);
        if(myDatabase.checkIfExists(owner,vehical)){
            Car detail=myDatabase.getCarData(owner,vehical);
           s1.setEnabled(detail.getExtra());

           spin.setSelection(getDefaultspinnerIndex());
            public int getDefaultSpinnerIndex() {
                for (int i = 0; i <parking.length; i++) {
                    if (parking[i].equals(detail.getParkig_type())) {
                        return i;
                    }
                }

                return 0;
            }
           s2.setEnabled(detail.isElectric_want());




        }
        else
        {

            btn.setEnabled(false);
            spin.setEnabled(false);
            s2.setEnabled(false);
        }




        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btn.setEnabled(b);
                    spin.setEnabled(b);
                    s2.setEnabled(b);

                }
                else {
                    btn.setEnabled(b);
                    spin.setEnabled(b);
                    s2.setEnabled(b);
                }
            }
        });


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ms=parking[i];

                Toast.makeText(ParkingActivity.this, "this is selcted"+ms, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean elect=s2.isChecked();
                int myInteger = elect ? 1 : 0;
                myDatabase.insertParking(owner,vehical,1,ms,myInteger);
                Toast.makeText(ParkingActivity.this, "succesfully submit ", Toast.LENGTH_SHORT).show();

            }
        });





    }


}