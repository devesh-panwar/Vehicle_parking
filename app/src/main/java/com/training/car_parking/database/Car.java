package com.training.car_parking.database;

public class Car {

    String owner,vehical_number,phone_number,parkig_type;
    boolean extra,electric_want;

    public String getParkig_type() {
        return parkig_type;
    }

    public  Car(String owner,String vehical_number,String phone_number,boolean extra,String parkig_type,boolean electric_want){
        this.owner=owner;
        this.vehical_number=vehical_number;
        this.phone_number=phone_number;
        this.extra=extra;
        this.parkig_type=parkig_type;
        this.electric_want=electric_want;
    }
    public void setParkig_type(String parkig_type) {
        this.parkig_type = parkig_type;
    }

    public boolean getExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public boolean isElectric_want() {
        return electric_want;
    }

    public void setElectric_want(boolean electric_want) {
        this.electric_want = electric_want;
    }

    public Car(String owner, String vehical_number, String phone_number) {
        this.owner = owner;
        this.vehical_number = vehical_number;
        this.phone_number = phone_number;

    }
        public Car(){

        }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVehical_number() {
        return vehical_number;
    }

    public void setVehical_number(String vehical_number) {
        this.vehical_number = vehical_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
