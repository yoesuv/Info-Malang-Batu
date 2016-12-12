package com.yoesuv.infomalangbatu.model;


public class PinMaps {

    private String name;
    private int lokasi;
    private double longitude,latitude;

    public PinMaps(String name, int lokasi , double longitude, double latitude){
        this.name = name;
        this.lokasi = lokasi;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLokasi() {
        return lokasi;
    }

    public void setLokasi(int lokasi) {
        this.lokasi = lokasi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
