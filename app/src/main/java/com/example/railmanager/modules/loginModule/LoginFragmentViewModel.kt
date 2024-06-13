package com.example.railmanager.modules.loginModule

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.userDbModule.CheckUserCallback
import com.example.railmanager.modules.dbModule.userDbModule.UserMethods

class LoginFragmentViewModel() : ViewModel() {

    fun login(context: Context, email: String, password: String) {

        val userMethods = UserMethods()

        if(!isValidMail(email)) {
            showSimpleAlertDialog(context, "Verifica che l'email sia inserita correttamente e riprova")
            return
        }

        if(!isValidPassword(password)) {
            showSimpleAlertDialog(context, "Verifica che la password sia inserita correttamente e riprova")
            return
        }

        checkAndLoginUser(context, email, password, userMethods)

    }

    // Chiamata al metodo di controllo utente con callback
    fun checkAndLoginUser(context: Context, email: String , password : String, userMethods: UserMethods) {
        userMethods.checkIfUserExists(context, email, object : CheckUserCallback {
            override fun onResult(exists: Boolean) {
                if (exists) {
                    // L'utente esiste, verifico che la password sia corretta
                    userMethods.checkIfPasswordIsCorrected(context, email, password, object : CheckUserCallback {
                        override fun onResult(exists: Boolean) {
                            if (exists) {
                                showSimpleAlertDialog(context,"Sei dentro")
                            }
                            else{
                                showSimpleAlertDialog(context,"Verifica che la password sia inserita correttamente e riprova")
                            }
                        }

                    })
                } else {
                    // L'utente non esiste, procedi con la registrazione
                    showSimpleAlertDialog(context, "L'utente non risulta registrato")
                }
            }
        })
    }

    /*Metodo che verifica la corretta formattazione dell'indirizzo email*/
    fun isValidMail(email : String) : Boolean{
        val emailRegex = Regex("^[a-zA-Z][a-zA-Z0-9._-]*@(gmail\\.com|outlook\\.com|yahoo\\.com|icloud\\.com|virgilio\\.it|libero\\.it|aruba\\.it|protonmail\\.com|namirial\\.com)\$")
        return emailRegex.matches(email)
    }

    /*Verifica la corretta formattazione della password*/
    fun isValidPassword(password : String) : Boolean{
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[!;#%&()*?@\\[\\]\\\\^\\-_{}])[A-Za-z0-9!;#%&()*?@\\[\\]\\\\^\\-_{}]{8,}$")
        return passwordRegex.matches(password)
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