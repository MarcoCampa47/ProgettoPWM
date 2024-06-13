package com.example.railmanager.modules.ticketsModule

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.userDbModule.UserMethods
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import android.app.AlertDialog

class TicketsRoutesFragmentViewModel() : ViewModel() {



    fun isValidDate(startDate: String , endDate: String): Int {
        var years : Int
        years = -1
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val startDate = LocalDate.parse(startDate, formatter)
            val endDate = LocalDate.parse(endDate , formatter)
            val yearsBetween = Period.between(startDate, endDate).days
            years =  yearsBetween
        } catch (e: DateTimeParseException) {
            Log.d("TAG1", "Invalid date format")
            null
        }
        return years
    }

    fun isValidNumberOfPassengers(adult : Int , child : Int) : Boolean{
        if(adult + child > 0){
            return true
        }
        return false
    }

    fun checkAllFields(context: Context, startDate : String , endDate : String , adult : Int , child : Int){


        if(isValidDate(startDate , endDate) < 0) {
            showSimpleAlertDialog(context, "Le date inserite non sono valide.")
            return
        }

        if(!isValidNumberOfPassengers(adult , child)){
            showSimpleAlertDialog(context, "Il numero di passeggeri non è valido.")
            return
        }




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