package com.yoesuv.infomalangbatu.utils;


import com.yoesuv.infomalangbatu.model.PinMaps;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

public interface PinsApiInterface {

    @GET("Maps_Malang_Batu.json")
    Call<List<PinMaps>> call();
}
