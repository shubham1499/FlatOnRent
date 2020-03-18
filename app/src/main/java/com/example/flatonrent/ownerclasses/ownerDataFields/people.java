package com.example.flatonrent.ownerclasses.ownerDataFields;

public class people {
     String city,emailid,firstname,lastname,phonenumber,uid;
     boolean flag;

    public people() {
    }

    public people(String city, String emailid, String firstname, String lastname, String phonenumber,boolean flag) {
        this.city = city;
        this.emailid = emailid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.flag=flag;
    }

    public people(String emailid, String city,boolean flag,String uid) {
        this.city = city;
        this.emailid = emailid;
        this.firstname = "";
        this.lastname = "";
        this.flag=flag;
        this.phonenumber="";
        this.uid=uid;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
