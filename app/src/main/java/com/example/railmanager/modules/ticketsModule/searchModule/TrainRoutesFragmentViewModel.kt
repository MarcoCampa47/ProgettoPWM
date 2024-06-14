package com.example.railmanager.modules.ticketsModule.searchModule

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.cityDbModule.CityMethods
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class TrainRoutesFragmentViewModel() : ViewModel() {

    val cityMethods = CityMethods()

    fun getAutoCompleteAdapterAllCities(context: Context, callback: (ArrayAdapter<String>) -> Unit) {
        cityMethods.getAllCities(context) { citiesHashMap ->
            val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, citiesHashMap.values.toList())
            callback(adapter)
        }
    }


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

    fun checkAllFields(context: Context, startDate : String, endDate : String, adult : Int, child : Int){


        if(isValidDate(startDate , endDate) < 0) {
            UsefulStaticMethods.showSimpleAlertDialog(context, "Le date inserite non sono valide.")
            return
        }

        if(!isValidNumberOfPassengers(adult , child)){
            UsefulStaticMethods.showSimpleAlertDialog(context, "Il numero di passeggeri non è valido.")
            return
        }




    }

}