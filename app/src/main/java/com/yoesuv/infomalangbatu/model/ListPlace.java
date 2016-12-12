package com.yoesuv.infomalangbatu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListPlace implements Parcelable{

    private String nama,lokasi,deskripsi,thumbnail,gambar;

    public ListPlace(){

    }

    public ListPlace(String n, String l, String d, String t, String g){
        this.nama = n;
        this.lokasi = l;
        this.deskripsi = d;
        this.thumbnail = t;
        this.gambar = g;
    }

    public ListPlace(Parcel source){
        nama = source.readString();
        lokasi = source.readString();
        deskripsi = source.readString();
        thumbnail = source.readString();
        gambar = source.readString();
    }

    public static Parcelable.Creator<ListPlace> CREATOR = new Parcelable.Creator<ListPlace>(){

        @Override
        public ListPlace createFromParcel(Parcel source) {
            return new ListPlace(source);
        }

        @Override
        public ListPlace[] newArray(int size) {
            return new ListPlace[size];
        }
    };

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        defaultValue();
        dest.writeString(nama);
        dest.writeString(lokasi);
        dest.writeString(deskripsi);
        dest.writeString(thumbnail);
        dest.writeString(gambar);
    }

    private void defaultValue(){
        if(nama==null){
            nama = "";
        }
        if(lokasi==null){
            lokasi = "";
        }
        if(deskripsi==null){
            deskripsi = "";
        }
        if(thumbnail==null){
            thumbnail = "";
        }
        if(gambar==null){
            gambar = "";
        }
    }
}
