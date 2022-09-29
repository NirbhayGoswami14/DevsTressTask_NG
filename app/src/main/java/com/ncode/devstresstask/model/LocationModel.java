package com.ncode.devstresstask.model;

public class LocationModel {
    String id;
    String locName;
    String lat;
    String lng;
    String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationModel(String id, String locName, String lat, String lng, String address) {
        this.id = id;
        this.locName = locName;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }
}
