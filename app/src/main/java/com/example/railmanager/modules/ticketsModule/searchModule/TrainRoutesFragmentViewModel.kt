package com.example.railmanager.modules.ticketsModule.searchModule

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.cityDbModule.CityMethods
import com.example.railmanager.modules.dbModule.searchDbModule.SearchRequest
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class TrainRoutesFragmentViewModel() : ViewModel() {

    val ticketsActivityViewModel = TicketsActivityViewModel
    val cityMethods = CityMethods()


    fun getAutoCompleteAdapterAllCities(
        context: Context,
        callback: (ArrayAdapter<String>) -> Unit
    ) {

        cityMethods.getAllCities(context) { obteinedMap ->
            ticketsActivityViewModel.setAllCitiesAndStationsHashMap(obteinedMap)
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_dropdown_item_1line,
                obteinedMap.keys.toList()
            )
            callback(adapter)
        }
    }


    fun search(
        fragment : Fragment,
        context: Context,
        startCity: String,
        endCity: String,
        departureDate: String,
        arrivalDate: String,
        adult: String,
        child: String
    ) {

        if (!(ticketsActivityViewModel.getAllCitiesAndStationsHashMap().keys.toList().contains(startCity) && ticketsActivityViewModel.getAllCitiesAndStationsHashMap().keys.toList().contains(endCity)) || startCity == endCity ) {
            UsefulStaticMethods.showSimpleAlertDialog(
                context,
                "Assicurati che i luoghi di partenza/arrivo appartengano alle città registrate"
            )
            return
        }

        //Coppie di ID <idcitta, idstazione>, se la città selezionata non ha stazioni allora la coppia sarà <idcittà, -1>
        val startPointCoupleId = ticketsActivityViewModel.getAllCitiesAndStationsHashMap()[startCity]!!
        val endPointCoupleId = ticketsActivityViewModel.getAllCitiesAndStationsHashMap()[endCity]!!

        //---------- Tengo traccia degli id delle città/stazioni selezionate
        var hashMapCoupleStartAndArrive = UsefulStaticMethods.getKeysForValues(ticketsActivityViewModel.getAllCitiesAndStationsHashMap(), listOf(startPointCoupleId, endPointCoupleId))
        ticketsActivityViewModel.setCitiesAndStationsSearchedHashMap(hashMapCoupleStartAndArrive)
        //----------

        val ticketsMethods = TicketsMethods()

        val searchRequest = SearchRequest(
            startPointCoupleId.first.toString(),
            startPointCoupleId.second.toString(),
            endPointCoupleId.first.toString(),
            endPointCoupleId.second.toString(),
            departureDate,
            arrivalDate,
            adult,
            child
        )

        ticketsMethods.runSearch(fragment, context, searchRequest)


    }

    fun isValidDate(startDate: String, endDate: String): Int {
        var years: Int
        years = -1
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val startDate = LocalDate.parse(startDate, formatter)
            val endDate = LocalDate.parse(endDate, formatter)
            val actualDate = Instant.now().atZone(ZoneId.systemDefault()).format(formatter)
            val actualDateFormatted = LocalDate.parse(actualDate, formatter)

            if (startDate.isBefore(actualDateFormatted)) {
                return -2
            }

            val yearsBetween = Period.between(startDate, endDate).days
            if (yearsBetween < 0) {
                return -1
            }
            years = yearsBetween
        } catch (e: DateTimeParseException) {
            Log.d("TAG1", "Invalid date format")
            null
        }
        return years
    }

    fun isValidStartAndEndDate(date: String): Int {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val startDate = LocalDate.parse(date, formatter)
            val actualDate = Instant.now().atZone(ZoneId.systemDefault()).format(formatter)
            val actualDateFormatted = LocalDate.parse(actualDate, formatter)

            if (startDate.isBefore(actualDateFormatted)) {
                return 0
            }
        } catch (ex: DateTimeParseException) {
            Log.d("TAG3", "Invalid date format")
            null
        }
        return 1
    }

    fun isValidNumberOfPassengers(adult: Int, child: Int): Boolean {
        if (adult + child > 0)
            return true
        else
            return false
    }

    fun checkAllFields(
        context: Context,
        startDate: String,
        endDate: String,
        adult: Int,
        child: Int
    ) : Boolean{

        if (!endDate.isEmpty()) {

            if(isValidStartAndEndDate(endDate) == 0){
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di arrivo non può essere precedente alla data di oggi."
                )
                return false
            }

            if (isValidDate(startDate, endDate) == -1) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "Le date inserite non sono valide."
                )
                return false
            }

            if (isValidDate(startDate, endDate) == -2) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di partenza non può essere precedente alla data di oggi."
                )
                return false
            }


        } else {
            if (isValidStartAndEndDate(startDate) == 0) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di partenza non può essere precedente alla data di oggi."
                )
                return false
            }
        }


        if (!isValidNumberOfPassengers(adult, child)) {
            UsefulStaticMethods.showSimpleAlertDialog(
                context,
                "Il numero di passeggeri non è valido."
            )
            return false
        }

        return true
    }



}