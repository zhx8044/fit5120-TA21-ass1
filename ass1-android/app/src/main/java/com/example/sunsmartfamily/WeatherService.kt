package com.example.sunsmartfamily
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("uvi")
    fun getUVIndex(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Call<UVIndexResponse>
}

data class UVIndexResponse(
    val lat: Double,
    val lon: Double,
    val date_iso: String,
    val date: Long,
    val value: Double  // 紫外线指数
)
