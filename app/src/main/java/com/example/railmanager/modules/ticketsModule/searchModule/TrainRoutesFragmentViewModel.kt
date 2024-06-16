package com.example.railmanager.modules.ticketsModule.searchModule

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.cityDbModule.CityMethods
import com.example.railmanager.modules.dbModule.searchDbModule.SearchRequest
import com.example.railmanager.modules.dbModule.ticketsDbModule.TicketsMethods
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class TrainRoutesFragmentViewModel() : ViewModel() {

    val cityMethods = CityMethods()

    //Mappa contenente le città e le stazioni associate
    var citiesAndStationsHashMap = HashMap<String, Pair<Int, Int>>()

    fun getAutoCompleteAdapterAllCities(
        context: Context,
        callback: (ArrayAdapter<String>) -> Unit
    ) {

        cityMethods.getAllCities(context) { obteinedMap ->
            citiesAndStationsHashMap = obteinedMap;
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_dropdown_item_1line,
                obteinedMap.keys.toList()
            )
            callback(adapter)
        }
    }

    fun search(
        context: Context,
        startCity: String,
        endCity: String,
        departureDate: String,
        arrivalDate: String,
        adult: String,
        child: String
    ) {

        if (!(citiesAndStationsHashMap.keys.toList()
                .contains(startCity) && citiesAndStationsHashMap.keys.toList().contains(endCity))
        ) {
            UsefulStaticMethods.showSimpleAlertDialog(
                context,
                "Assicurati che i luoghi di partenza/arrivo appartengano alle città registrate"
            )
            return
        }

        //Coppie di ID <idcitta, idstazione>, se la città selezionata non ha stazioni allora la coppia sarà <idcittà, -1>
        val startPointCoupleId = citiesAndStationsHashMap[startCity]!!
        val endPointCoupleId = citiesAndStationsHashMap[endCity]!!

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

        ticketsMethods.runSearch(context, searchRequest)

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
        if (adult + child > 0) {
            return true
        }
        return false
    }

    fun checkAllFields(
        context: Context,
        startDate: String,
        endDate: String,
        adult: Int,
        child: Int
    ) {

        if (!endDate.isEmpty()) {

            if(isValidStartAndEndDate(endDate) == 0){
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di arrivo non può essere precedente alla data di oggi."
                )
                return
            }

            if (isValidDate(startDate, endDate) == -1) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "Le date inserite non sono valide."
                )
                return
            }

            if (isValidDate(startDate, endDate) == -2) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di partenza non può essere precedente alla data di oggi."
                )
                return
            }


        } else {
            if (isValidStartAndEndDate(startDate) == 0) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "La data di partenza non può essere precedente alla data di oggi."
                )
            }



            if (!isValidNumberOfPassengers(adult, child)) {
                UsefulStaticMethods.showSimpleAlertDialog(
                    context,
                    "Il numero di passeggeri non è valido."
                )
                return
            }


        }

    }
}