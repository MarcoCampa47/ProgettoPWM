package com.example.railmanager.modules.ticketsModule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object TicketsActivityViewModel : ViewModel() {

    private var idutente : Int = -1;

    private val _emailUtente = MutableLiveData<String>()
    val emailUtente : LiveData<String>
        get() = _emailUtente

    private val _adultsNumberWhoWantsToBuy = MutableLiveData<Int>()
    val adultsNumberWhoWantsToBuy : LiveData<Int>
        get() = _adultsNumberWhoWantsToBuy

    private val _minorsNumberWhoWantsToBuy = MutableLiveData<Int>()
    val minorsNumberWhoWantsToBuy : LiveData<Int>
        get() = _minorsNumberWhoWantsToBuy

    private val _startLocation = MutableLiveData<String>()
    val startLocation : LiveData<String>
        get() = _startLocation

    private val _endLocation = MutableLiveData<String>()
    val endLocation : LiveData<String>
        get() = _endLocation

    private val _citiesAndStationsSearchedHashMap = MutableLiveData<HashMap<String,Pair<Int,Int>>>()
    val citiesAndStationsSearchedHashMap : LiveData<HashMap<String,Pair<Int,Int>>>
        get() = _citiesAndStationsSearchedHashMap

    private val _allCitiesAndStationsHashMap = MutableLiveData<HashMap<String,Pair<Int,Int>>>()
    val allCitiesAndStationsHashMap : LiveData<HashMap<String,Pair<Int,Int>>>
        get() = _allCitiesAndStationsHashMap

    fun setEmail(email : String){
        _emailUtente.value = email
        Log.d("EMAIL UTENTE", _emailUtente.value!!)
    }

    fun setAdultsNumber(adults : Int){
        _adultsNumberWhoWantsToBuy.value = adults
        Log.d("ADULTI", _adultsNumberWhoWantsToBuy.value.toString())
    }

    fun setMinorsNumber(minors : Int){
        _minorsNumberWhoWantsToBuy.value = minors
        Log.d("MINORI", _minorsNumberWhoWantsToBuy.value.toString())
    }

    fun setCitiesAndStationsSearchedHashMap(citiesAndStationsSearchedHashMap : HashMap<String,Pair<Int,Int>>){
        _citiesAndStationsSearchedHashMap.value = citiesAndStationsSearchedHashMap
        Log.d("MAPPA RICERCA", _citiesAndStationsSearchedHashMap.value.toString() )
    }

    fun setAllCitiesAndStationsHashMap(allCitiesAndStationsHashMap : HashMap<String,Pair<Int,Int>>){
        _allCitiesAndStationsHashMap.value = allCitiesAndStationsHashMap
        Log.d("MAPPA TUTTE", _allCitiesAndStationsHashMap.value.toString() )
    }

    fun getAllCitiesAndStationsHashMap() : HashMap<String,Pair<Int,Int>>{
        return _allCitiesAndStationsHashMap.value!!
    }

    fun setIdUtente(id : Int){
     this.idutente = id;
    }

    fun getIdUtente() : Int{
        return idutente;
    }

    fun getAdultsNumberWhoWantsToBuy() : Int{
        return _adultsNumberWhoWantsToBuy.value!!
    }

    fun getMinorsNumberWhoWantsToBuy() : Int{
        return _minorsNumberWhoWantsToBuy.value!!
    }

}