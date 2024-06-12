package com.example.railmanager.modules.dbModule
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserInterface {
    @GET("railmanager/user/{id}")
    fun getUserById(@Path("id") userId: Int): Call<User>

    @POST("railmanager/user/checkEmail")
    fun checkIfUserExists(@Body email : String) : Call<User>
}