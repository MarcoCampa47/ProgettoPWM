package com.example.railmanager.modules.registrationModule

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.userDbModule.CheckUserCallback
import com.example.railmanager.modules.dbModule.userDbModule.UserMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivity
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class RegistrationFragmentViewModel () : ViewModel() {


    fun checkAllFields(context: Context, name : String, surname : String, email: String , password : String, date : String, phoneNumber : String){
        //Ai metodi della classe UserMethods va passato il context, affinché possa comparire l'alert dialog in caso di errore
        val userMethods = UserMethods()

        if(!isValidNameAndSurname(name, surname)) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Nome e Cognome non validi")
            return
        }

        if(!isValidMail(email)) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Email non valida")
            return
        }

        if(!isValidPassword(password)) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Password non valida")
            return
        }

        if(isValidDate(date)!! < 14) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Data di nascita non valida")
            return
        }

        if(!isValidPhoneNumber(phoneNumber)) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Numero di telefono non valido")
            return
        }

        this.checkAndRegisterUser(context, name, surname, email, password, date, phoneNumber, userMethods)


    }

    // Chiamata al metodo di controllo utente con callback
    fun checkAndRegisterUser(context: Context, name : String, surname : String, email: String , password : String, date : String, phoneNumber : String, userMethods: UserMethods) {

        userMethods.checkIfUserExists(context, email, object : CheckUserCallback {
            override fun onResult(exists: Int) {
                if (exists != -1) {
                    // L'utente esiste già
                    UsefulStaticMethods.showSimpleAlertDialog(context, "Utente già registrato")
                } else {
                    // L'utente non esiste, procedi con la registrazione
                    userMethods.registerUser(context, name, surname, email, password, date, phoneNumber){
                        userId -> if (userId != null) {
                            val intent = Intent(context, TicketsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("e-mail", email)
                            intent.putExtra("idUtente", userId)
                            context.startActivity(intent)
                        }
                        else{
                            UsefulStaticMethods.showSimpleAlertDialog(context, "Errore durante la registrazione")
                        }
                    }

                }
            }
        })

    }


    /*Verifica la corretta formattazione del nome e del cognome*/
    fun isValidNameAndSurname(name : String , surname: String) : Boolean{
        val nameAndSurnameRegex = Regex("^[a-zA-Z]{2,}\$")

        return nameAndSurnameRegex.matches(name) && nameAndSurnameRegex.matches(surname)
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

    /*Metodo per verificare che la data di nascita rispetti i criteri prestabiliti*/
    fun isValidDate(date: String): Int {
        var years : Int
        years = -1
         try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = LocalDate.parse(date, formatter)
            val currentDate = LocalDate.now()
            val yearsBetween = Period.between(selectedDate, currentDate).years
            years =  yearsBetween
        } catch (e: DateTimeParseException) {
            Log.d("TAG1", "Invalid date format")
            null
        }
        return years
    }

    /*Metodo per verificare che il numero di telefono sia correttamente formattato*/
    fun isValidPhoneNumber(phone : String) : Boolean{
        val phoneRegex = Regex("^\\+[0-9]{1,3}[0-9]{10}\$")
        return phoneRegex.matches(phone)
    }



}