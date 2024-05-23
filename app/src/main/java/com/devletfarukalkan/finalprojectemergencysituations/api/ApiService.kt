package com.devletfarukalkan.finalprojectemergencysituations.api

import retrofit2.http.GET
import retrofit2.Call
import com.devletfarukalkan.finalprojectemergencysituations.entities.ApiResponse

interface ApiService {
    @GET("api/v1/neighborhoods")
    fun getNeighborhoods(): Call<ApiResponse>
}