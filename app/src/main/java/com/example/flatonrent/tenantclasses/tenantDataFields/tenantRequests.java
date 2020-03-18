package com.example.flatonrent.tenantclasses.tenantDataFields;

public class tenantRequests {
    String Date,Status,Time,ouid;

    public tenantRequests(String date, String status, String time, String ouid) {
        Date = date;
        Status = status;
        Time = time;
        this.ouid = ouid;
    }

    public tenantRequests() {
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

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }
}
