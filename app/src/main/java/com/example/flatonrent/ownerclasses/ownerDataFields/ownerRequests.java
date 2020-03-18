package com.example.flatonrent.ownerclasses.ownerDataFields;

public class ownerRequests {
    String Date,Status,Time,tuid;


    public ownerRequests(String date, String status, String time, String tuid) {
        Date = date;
        Status = status;
        Time = time;
        this.tuid = tuid;
    }

    public ownerRequests() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String gettuid() {
        return tuid;
    }

    public void settuid(String tuid) {
        this.tuid = tuid;
    }
}
