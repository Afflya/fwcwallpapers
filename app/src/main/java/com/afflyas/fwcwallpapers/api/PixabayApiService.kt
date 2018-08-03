package com.afflyas.fwcwallpapers.api

import com.afflyas.fwcwallpapers.repository.PixabayImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {

    companion object {
        const val API_KEY = "9711400-51a476dcb6d8ccb45a56bedb6"
        const val BASE_URL = "https://pixabay.com/api/"
    }

    @GET("?key=$API_KEY&image_type=all&per_page=200&category=sports")
    fun getImages(@Query("q") term: String): Call<ApiResponse<PixabayImage>>

}