package com.example.railmanager.modules.dbModule.userDbModule
import android.content.Context
import com.example.railmanager.modules.dbModule.loginDbModule.LoginRequest
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

    @POST("railmanager/user/checkEmailAndPassword")
    fun checkIfPasswordIsCorrected(@Body loginRequest: LoginRequest) : Call<User>

    @POST("railmanager/user/register")
    fun registerUser(@Body user : User) : Call<User>
}