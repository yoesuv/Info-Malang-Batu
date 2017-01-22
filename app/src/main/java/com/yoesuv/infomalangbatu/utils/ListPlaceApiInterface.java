package com.yoesuv.infomalangbatu.utils;

import com.yoesuv.infomalangbatu.model.ListPlace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ListPlaceApiInterface {

    @GET("List_place_malang_batu.json")
    Call<List<ListPlace>> callSemua();

    @GET("List_place_kota_malang.json")
    Call<List<ListPlace>> callkotaMalang();

    @GET("List_place_kota_batu.json")
    Call<List<ListPlace>> callkotaBatu();

    @GET("List_place_kab_malang.json")
    Call<List<ListPlace>> callkabMalang();
}
