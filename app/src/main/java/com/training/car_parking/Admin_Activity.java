package com.training.car_parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.training.car_parking.database.Car;
import com.training.car_parking.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Admin_Activity extends AppCompatActivity {
    MyDatabase myDatabase;
    ListView listView;

    List<String> setable_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView=findViewById(R.id.view);
        myDatabase=new MyDatabase(this);
        List<Car> list=myDatabase.getAllRecord();

        for(Car car: list){
            setable_list.add("owner name : "+car.getOwner() +"\n vehical number :"+car.getVehical_number()+"\n phone number :"+car.getPhone_number());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,setable_list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //               // here i have to write code of itenfication of which item is selected

                String selectedItem = (String) adapterView.getItemAtPosition(i).toString();
              //  Toast.makeText(Admin_Activity.this, " this selected "+selectedItem, Toast.LENGTH_SHORT).show();
                // Retrieve the corresponding Car object from the list or database
                Car selectedCar = list.get(i);

                // Retrieve specific attributes of the selected car
                String ownerName = selectedCar.getOwner();
                String vehicleNumber = selectedCar.getVehical_number();
                String phoneNumber = selectedCar.getPhone_number();

                // Perform any actions or display information about the selected item
                // For example, you can show a Toast message with the selected item's details
                String message = "Selected item: \nOwner Name: " + ownerName + "\nVehicle Number: " + vehicleNumber + "\nPhone Number: " + phoneNumber;
//                Toast.makeText(Admin_Activity.this, "owner"+ownerName, Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Admin_Activity.this,admincarview.class);
                intent.putExtra("key_owner",ownerName);
                intent.putExtra("key_vehical",vehicleNumber);
                startActivity(intent);
            //    Toast.makeText(Admin_Activity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("selected","you selected");
                Toast.makeText(Admin_Activity.this, " you clicked somthing", Toast.LENGTH_SHORT).show();
//               // here i have to write code of itenfication of which item is selected
//
//                String selectedItem = (String) adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(Admin_Activity.this, " this selected "+selectedItem, Toast.LENGTH_SHORT).show();
//                // Retrieve the corresponding Car object from the list or database
//                Car selectedCar = list.get(i);
//
//                // Retrieve specific attributes of the selected car
//                String ownerName = selectedCar.getOwner();
//                String vehicleNumber = selectedCar.getVehical_number();
//                String phoneNumber = selectedCar.getPhone_number();
//
//                // Perform any actions or display information about the selected item
//                // For example, you can show a Toast message with the selected item's details
//                String message = "Selected item: \nOwner Name: " + ownerName + "\nVehicle Number: " + vehicleNumber + "\nPhone Number: " + phoneNumber;
//                Toast.makeText(Admin_Activity.this, message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("edit");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index=info.position;
        switch (item.getTitle().toString()){

            case "edit":{
                Dialog dialog=new Dialog(Admin_Activity.this);
                dialog.setContentView(R.layout.editable_record);
                dialog.show();
                Button btn1=dialog.findViewById(R.id.button);
                EditText et1=dialog.findViewById(R.id.editTextText);
                EditText et2=dialog.findViewById(R.id.editTextText2);
                //dialog.setCancelable(false);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newowner=et1.getText().toString();
                        String newnumber=et2.getText().toString();
                        if(!(newnumber.isEmpty() && newowner.isEmpty())){

                        }
                        else {
                            Toast.makeText(Admin_Activity.this, "put somthing contain ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
            }
        }
        return super.onContextItemSelected(item);
    }
}