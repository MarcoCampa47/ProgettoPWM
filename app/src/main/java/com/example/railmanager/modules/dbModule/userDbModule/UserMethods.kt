package com.example.railmanager.modules.dbModule.userDbModule

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import com.example.railmanager.modules.dbModule.loginDbModule.LoginRequest
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserMethods {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.58:9000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApiService = retrofit.create(UserInterface::class.java)
    fun checkIfUserExists(context: Context, email : String, callback: CheckUserCallback){
        val requestBody = RequestBody.create(MediaType.parse("application/json"), email)
        val call = userApiService.checkIfUserExists(email)
        var result = false;
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    if(user != null && user.email == email) {
                        callback.onResult(true)
                    }
                    else{
                        Log.d("DEBUG", "L'utente non esiste")
                        callback.onResult(false)
                    }
                }
            }

            override fun onFailure(p0: Call<User>, t: Throwable) {
                Log.d("DEBUG","Request failed with response code: ${t.message}")
                callback.onResult(false)
            }

        })

    }

    fun checkIfPasswordIsCorrected(context: Context, email: String, password: String, callback: CheckUserCallback){

        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
        val loginRequest = LoginRequest(email,password)
        val call = userApiService.checkIfPasswordIsCorrected(loginRequest)
        var result = false;
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    if(user != null && user.email == email && user.password == password) {
                        callback.onResult(true)
                    }
                    else{
                        Log.d("DEBUG", "Verifica che E-mail e password siano corretti")
                        callback.onResult(false)
                    }
                }
            }

            override fun onFailure(p0: Call<User>, t: Throwable) {
                Log.d("DEBUG","Request failed with response code: ${t.message}")
                callback.onResult(false)
            }

        })
    }

    fun registerUser(context: Context, name : String, surname : String, email: String , password : String, date : String, phoneNumber : String) : Boolean{
        val json = JSONObject()
        json.put("name", name)
        json.put("surname", surname)
        json.put("email", email)
        json.put("password", password)
        json.put("date", date)
        json.put("phoneNumber", phoneNumber)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

        val userToRegister = User(-1,email,name,surname,password,date,phoneNumber)

        val call = userApiService.registerUser(userToRegister)
        var result : Boolean = false;

        call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        val responseResult = response.body()
                        if(responseResult != null && responseResult.email.toBoolean()) {
                            showSimpleAlertDialog(context, "Ti sei registrato")
                            Log.d("DEBUG"," ${responseResult}")
                        }
                    }
                    else{
                        Log.d("DEBUG","Request failed with response code: ${response.code()}")
                        showSimpleAlertDialog(context, "Si è verificato un errore imprevisto")
                    }
                }

                override fun onFailure(p0: Call<User>, p1: Throwable) {
                    showSimpleAlertDialog(context, "Si è verificato un errore imprevisto")
                    Log.d("DEBUG","Request failed with response code: ${p1.message}" )
                }


        })


        return result
    }

    /*Da adattare bene, al momento solo per test*/
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

    private fun showSimpleAlertDialog(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}