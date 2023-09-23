package com.mkrdeveloper.retrofitgetjetpack.data

import com.mkrdeveloper.retrofitgetjetpack.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("posts/{id}")
    suspend fun getUserByNumber(
        @Path("id") id:Int
    ): Response<User>
}