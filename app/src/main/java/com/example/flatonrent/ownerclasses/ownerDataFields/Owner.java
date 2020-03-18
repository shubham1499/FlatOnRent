package com.example.flatonrent.ownerclasses.ownerDataFields;

public class Owner {
    people          pd;
    flatDetails     fd;

    public Owner() {
    }

    public Owner(people pd, flatDetails fd) {
        this.pd = pd;
        this.fd = fd;
    }

    public people getPd() {
        return pd;
    }

    public void setPd(people pd) {
        this.pd = pd;
    }

    public flatDetails getFd() {
        return fd;
    }

    public void setFd(flatDetails fd) {
        this.fd = fd;
    }
}
