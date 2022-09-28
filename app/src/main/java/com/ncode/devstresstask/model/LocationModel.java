package com.ncode.devstresstask.model;

public class LocationModel {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String locName;
    String locLatLang;
    String address;

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocLatLang() {
        return locLatLang;
    }

    public void setLocLatLang(String locLatLang) {
        this.locLatLang = locLatLang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationModel(String id,String locName, String locLatLang, String address) {
       this.id=id;
        this.locName = locName;
        this.locLatLang = locLatLang;
        this.address = address;
    }
}
