package com.training.car_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.training.car_parking.database.Car;
import com.training.car_parking.database.MyDatabase;


public class admincarview extends AppCompatActivity {


    MyDatabase myDatabase;
    TextView owe,gaddi,phone,ex,parktype,electric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admincarview);
        owe=findViewById(R.id.textView3);
        gaddi=findViewById(R.id.textView5);
        phone=findViewById(R.id.textView7);
        ex=findViewById(R.id.textView9);
        parktype=findViewById(R.id.textView11);
        electric=findViewById(R.id.textView13);
        myDatabase=new MyDatabase(this);
        Bundle b=getIntent().getExtras();
        String name=b.getString("key_owner");
        String vh_no=b.getString("key_vehical");

        Car detail=myDatabase.getCarData(name,vh_no);
        owe.setText(detail.getOwner());
        gaddi.setText(detail.getVehical_number());

       // Toast.makeText(this, " this is testing" + name, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "  "+detail.getOwner(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "parkign type" +detail.getParkig_type(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "phone number "+detail.getPhone_number(), Toast.LENGTH_SHORT).show();
        phone.setText(detail.getPhone_number());
        if(String.valueOf(detail.getExtra()).equals("true"))
            ex.setText("Yes");
        else ex.setText("No");
        parktype.setText(""+detail.getParkig_type());

        if(String.valueOf(detail.isElectric_want()).equals("true"))
            electric.setText("Yes");
        else electric.setText("No");



    }
}