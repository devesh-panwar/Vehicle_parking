package com.training.car_parking.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.training.car_parking.ParkingActivity;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public static final int DATA_BASE_VERSION=1;
    public static final String DATA_BASE_NAME="mydb";
public static final String TABLE_NAME="car";

    public static final String OWNER="owner";
    public static final String CAR_NO="vehical_number";
    public static final String PHONE_NUMBER="phone_number";
    public String parking_type="parking_type";
    public String extra="extra";
    public String electric_want="electric_want";

   public MyDatabase(Context context){
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="CREATE TABLE "+TABLE_NAME+ "(" +OWNER+" TEXT NOT NULL,"+CAR_NO +" TEXT NOT NULL,"+PHONE_NUMBER+ " TEXT," +
                "extra INTEGER DEFAULT 0, " +
                "parking_type TEXT DEFAULT 'simple', " +
                "electric_want INTEGER DEFAULT 0, "+
                ""+" PRIMARY KEY("+OWNER+","+CAR_NO+") )";

        sqLiteDatabase.execSQL(query);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertParking(String owner, String carNo, int extra1, String parkingType, int electricWants) {

        String pn=null;
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase sql=getReadableDatabase();
        String selection = OWNER + " =? AND " + CAR_NO + " =?";
        String selctionValue[]={owner,carNo};
        String phone_temp[]={PHONE_NUMBER};
        Cursor temp=sql.query(TABLE_NAME,phone_temp,selection,selctionValue,null,null,null);
        if(temp.moveToFirst() )
        { int index=temp.getColumnIndex(PHONE_NUMBER);
             pn=temp.getString(index);
        temp.close();}
        ContentValues values = new ContentValues();
        values.put(OWNER, owner);
        values.put(CAR_NO, carNo);
        values.put(PHONE_NUMBER,pn);
        values.put(extra, extra1);
        values.put(parking_type, parkingType);
        values.put(electric_want, electricWants);


//        String selection = "OWNER = ? AND CAR_NO = ?";
//        String[] selectionArgs = {owner, carNo};
//
//        int rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
//        if (rowsUpdated == 0) {
//            // Row doesn't exist, so we need to insert a new one
//            db.insert(TABLE_NAME, null, values);
//        }
//
//        db.close();

       db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public long insertRecored(Car car){
        //String query="INSERT INTO "+TABLE_NAME+"VALUES ("+car.getOwner()

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(OWNER,car.getOwner());
        values.put(CAR_NO,car.vehical_number);
        values.put(PHONE_NUMBER,car.getPhone_number());

        return sqLiteDatabase.insert(TABLE_NAME,null,values);
    }

    public long delete(String owner,String vehical_number){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,owner+ vehical_number+"?",new String[]{owner,vehical_number});
    }

    public List<Car> getAllRecord(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        List<Car> record=new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Car car=new Car();
                car.setOwner(cursor.getString(0));
                car.setVehical_number(cursor.getString(1));
                car.setPhone_number(cursor.getString(2));

                record.add(car);
            }while (cursor.moveToNext());
        } return record;
    }

    public boolean checkIfExists(String owner, String vehicleNumber) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // Define the columns you want to retrieve from the table
       // String[] columns = {COLUMN_NAME};

        // Define the selection criteria
        String selection = OWNER + " =? AND " + CAR_NO + " =?";
        String[] selectionArgs = {owner, vehicleNumber};

        // Execute the query
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        // Check if any matching record was found
        boolean exists = (cursor != null && cursor.getCount() > 0);

                if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

        return exists;
    }

    public Car getCarData(String owner1, String carNo1) {
        Car car = null;
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {PHONE_NUMBER,extra, parking_type, electric_want};
        String selection = OWNER +"=? AND "+CAR_NO +"=?";
        String[] selectionArgs = {owner1, carNo1};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst())
        {
            int pno=cursor.getColumnIndex(PHONE_NUMBER);
            int extraIndex = cursor.getColumnIndex(extra);
            int parkingTypeIndex = cursor.getColumnIndex(parking_type);
            int electricWantsIndex = cursor.getColumnIndex(electric_want);

            boolean extra = cursor.getInt(extraIndex)==1;
            String pn=cursor.getString(pno);
            String parkingType = cursor.getString(parkingTypeIndex);
            boolean electricWants = cursor.getInt(electricWantsIndex)==1;

            car = new Car(owner1, carNo1,pn, extra, parkingType, electricWants);

            cursor.close();
        }

        return car;
    }

//    public Car admin_view (String owner,String vehicalNumber){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        String selection = OWNER + " = ? AND " + CAR_NO + " = ?";
//        String[] selectionArgs = {owner, vehicalNumber,PHONE_NUMBER,extra,parking_type,electric_want};
//        String query="SELECT "+PHONE_NUMBER+","+extra+","+parking_type+","+electric_want+ "FROM" +TABLE_NAME + "WHERE "+OWNER +"="+owner+ "&" +CAR_NO+"="+vehicalNumber;
//        Car detail=sqLiteDatabase.rawQuery(query,null);
//        return cursor;
//    }

    public boolean update_owner_vechical(String owner, String vehicleNumber) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // Define the columns you want to retrieve from the table
        // String[] columns = {COLUMN_NAME};

        // Define the selection criteria
        String selection = OWNER + " = ? AND " + CAR_NO + " = ?";
        String[] selectionArgs = {owner, vehicleNumber};

        // Execute the query
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        // Check if any matching record was found
        boolean exists = (cursor != null && cursor.getCount() > 0);

        // Close the cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

       return exists;
    }

}
