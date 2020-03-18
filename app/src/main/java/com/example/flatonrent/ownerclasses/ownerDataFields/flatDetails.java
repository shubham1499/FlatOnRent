package com.example.flatonrent.ownerclasses.ownerDataFields;

import android.os.Parcel;
import android.os.Parcelable;

public class flatDetails implements Parcelable {
 String flataddress,apartmentname,city,state,tenant,area,landmark,uid;
 int BHK,deposit,flatno,floor,rent,maintainence,apartmentno;
 boolean furnished,parking,lift,flag;

    public flatDetails(String uid) {
        this.flataddress = "";
        this.apartmentname = "";
        this.city = "";
        this.parking = false;
        this.state = "";
        this.tenant = "";
        this.BHK = -1;
        this.deposit = -1;
        this.flatno = -1;
        this.floor = -1;
        this.furnished = false;
        this.maintainence = -1;
        this.rent = -1;
        this.lift = false;
        this.area = "";
        this.landmark = "";
        this.apartmentno = -1;
        this.flag = false;
        this.uid=uid;
    }

    public flatDetails(int BHK, boolean furnished, boolean parking, boolean lift,String uid) {
        this.BHK = BHK;
        this.furnished = furnished;
        this.parking = parking;
        this.lift = lift;
        this.uid=uid;
    }


    protected flatDetails(Parcel in) {
        flataddress = in.readString();
        apartmentname = in.readString();
        city = in.readString();
        state = in.readString();
        tenant = in.readString();
        area = in.readString();
        landmark = in.readString();
        BHK = in.readInt();
        deposit = in.readInt();
        flatno = in.readInt();
        floor = in.readInt();
        rent = in.readInt();
        maintainence = in.readInt();
        apartmentno = in.readInt();
        furnished = in.readByte() != 0;
        parking = in.readByte() != 0;
        lift = in.readByte() != 0;
        flag = in.readByte() != 0;
        uid = in.readString();
    }

    public static final Creator<flatDetails> CREATOR = new Creator<flatDetails>() {
        @Override
        public flatDetails createFromParcel(Parcel in) {
            return new flatDetails(in);
        }

        @Override
        public flatDetails[] newArray(int size) {
            return new flatDetails[size];
        }
    };

    public flatDetails() {
    }

    public String getFlataddress() {
        return flataddress;
    }

    public void setFlataddress(String flataddress) {
        this.flataddress = flataddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public int getBHK() {
        return BHK;
    }

    public void setBHK(int BHK) {
        this.BHK = BHK;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getFlatno() {
        return flatno;
    }

    public void setFlatno(int flatno) {
        this.flatno = flatno;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getMaintainence() {
        return maintainence;
    }

    public void setMaintainence(int maintainence) {
        this.maintainence = maintainence;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getApartmentname() {
        return apartmentname;
    }

    public void setApartmentname(String apartmentname) {
        this.apartmentname = apartmentname;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }

    public int getApartmentno() {
        return apartmentno;
    }

    public void setApartmentno(int apartmentno) {
        this.apartmentno = apartmentno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(flataddress);
        parcel.writeString(apartmentname);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(tenant);
        parcel.writeString(area);
        parcel.writeString(landmark);
        parcel.writeInt(BHK);
        parcel.writeInt(deposit);
        parcel.writeInt(flatno);
        parcel.writeInt(floor);
        parcel.writeInt(rent);
        parcel.writeInt(maintainence);
        parcel.writeInt(apartmentno);
        parcel.writeByte((byte) (furnished ? 1 : 0));
        parcel.writeByte((byte) (parking ? 1 : 0));
        parcel.writeByte((byte) (lift ? 1 : 0));
        parcel.writeByte((byte) (flag ? 1 : 0));
        parcel.writeString(uid);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
