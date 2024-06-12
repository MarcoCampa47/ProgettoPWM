package com.example.railmanager.modules.registrationModule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.EmailRequest
import com.example.railmanager.modules.dbModule.User
import com.example.railmanager.modules.dbModule.UserInterface
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationFragmentViewModel : ViewModel() {
    lateinit var email : String;
    lateinit var password : String;


    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
     .addConverterFactory(GsonConverterFactory.create())
     .build()

    val userApiService = retrofit.create(UserInterface::class.java)

    fun getUserInfo(userId: Int) {
        val call = userApiService.getUserById(userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                        if(user != null && user.name != null && user.password != null){
                                Log.d("DEBUG","User ID: ${user.iduser}")
                                Log.d("DEBUG","User Name: ${user.name}")
                                Log.d("DEBUG","User password: ${user.password}")
                        }
                        else{
                            Log.d("DEBUG", "Utente non trovato")
                        }
                } else {
                    Log.d("DEBUG","Request failed with response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun checkIfUserExists(email : String){
        val requestBody = RequestBody.create(MediaType.parse("application/json"), email)
        val call = userApiService.checkIfUserExists(email)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    if(user != null && user.email == email) {
                        Log.d("DEBUG", "L'utente esiste già")
                    }
                    else{
                        Log.d("DEBUG", "L'utente non esiste")
                    }
                }
            }

            override fun onFailure(p0: Call<User>, t: Throwable) {
                Log.d("DEBUG","Request failed with response code")
            }

        })
    }

    fun checkAllFields(email: String){
        isValidMail(email)
    }

    /*Metodo che verifica la corretta formattazione dell'indirizzo email*/
    fun isValidMail(email : String) : Boolean{



        val emailRegex = Regex("^[a-zA-Z][a-zA-Z0-9._-]*@(gmail\\.com|outlook\\.com|yahoo\\.com|icloud\\.com|virgilio\\.it|libero\\.it|aruba\\.it|protonmail\\.com|namirial\\.com)\$")

        return emailRegex.matches(email)
    }


}