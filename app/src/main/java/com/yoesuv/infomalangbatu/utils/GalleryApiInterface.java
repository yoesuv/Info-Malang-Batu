package com.yoesuv.infomalangbatu.utils;


import com.yoesuv.infomalangbatu.model.InfoGallery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GalleryApiInterface {

    @GET("Gallery_Malang_Batu.json")
    Call<List<InfoGallery>> callGallery();
}
