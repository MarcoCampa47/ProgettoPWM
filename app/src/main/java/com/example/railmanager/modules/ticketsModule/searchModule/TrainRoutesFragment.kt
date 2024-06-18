package com.example.railmanager.modules.ticketsModule.searchModule

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.railmanager.R
import com.example.railmanager.modules.ticketsModule.TicketsActivity
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel
import com.example.railmanager.modules.ticketsModule.boughtTicketsModule.BoughtTicketsFragment
import com.example.railmanager.modules.ticketsModule.boughtTicketsModule.BoughtTicketsFragmentViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class TrainRoutesFragment : Fragment() {

    val trainRoutesFragmentViewModel : TrainRoutesFragmentViewModel by viewModels()
    private val ticketsActivityViewModel : TicketsActivityViewModel by viewModels()
            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_train_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDate: EditText = view.findViewById(R.id.StartEditTextDateTrainRoutesFragment)
        val endDate: EditText = view.findViewById(R.id.EndEditTextDateTrainRoutesFragment)
        val adults: TextView = view.findViewById(R.id.NumberOfAdultsTextViewTrainRoutesFragment)
        val adultsPlusButton: ImageView = view.findViewById(R.id.plusAdultImageViewTrainRouteFragment)
        val adultsMinusButton: ImageView =
            view.findViewById(R.id.minusAdultImageViewTrainRouteFragment)
        val minors: TextView = view.findViewById(R.id.NumberOfMinorsTextViewTrainRoutesFragment)
        val minorsPlusButton: ImageView = view.findViewById(R.id.plusMinorsImageViewTrainRouteFragment)
        val minorsMinusButton: ImageView =
            view.findViewById(R.id.minusMinorsImageViewTrainRouteFragment)
        val searchButton: Button = view.findViewById(R.id.searchButtonTrainRoutesFragment)


        val startPoint = view.findViewById<AutoCompleteTextView>(R.id.startPointAutoCompTrainRoutesFragment)
        val endPoint = view.findViewById<AutoCompleteTextView>(R.id.endPointAutoCompTrainRoutesFragment)

        this.context?.let {
            trainRoutesFragmentViewModel.getAutoCompleteAdapterAllCities(it) { adapter ->
                startPoint.setAdapter(adapter)
                startPoint.threshold = 0
                endPoint.setAdapter(adapter)
                endPoint.threshold = 0
            }
        }


        startDate.showSoftInputOnFocus = false
        endDate.showSoftInputOnFocus = false

        startDate.setOnClickListener {
            openCalendar(R.id.StartEditTextDateTrainRoutesFragment)
        }
        endDate.setOnClickListener {
            openCalendar(R.id.EndEditTextDateTrainRoutesFragment)
        }



        searchButton.setOnClickListener {
            this.context?.let { ct ->
                if(trainRoutesFragmentViewModel.checkAllFields(
                        ct,
                        startDate.text.toString(),
                        endDate.text.toString(),
                        Integer.parseInt(adults.text.toString()),
                        Integer.parseInt(minors.text.toString())
                )){
                    ticketsActivityViewModel.setAdultsNumber(adults.text.toString().toInt())
                    ticketsActivityViewModel.setMinorsNumber(minors.text.toString().toInt())
                    trainRoutesFragmentViewModel.search(this,  ct, startPoint.text.toString().trim(), endPoint.text.toString().trim() , startDate.text.toString(), endDate.text.toString(), adults.text.toString(), minors.text.toString() )
                }

            }



        }

        // Listener per il cambiamento di testo della TextView degli adulti
        adults.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Non necessario implementare
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando il testo cambia, aggiorna il LiveData nel ViewModel
                s?.toString()?.let { text ->
                    val number = if (text.isEmpty()) 0 else text.toInt()
                    ticketsActivityViewModel.setAdultsNumber(number)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Non necessario implementare
            }
        })

        adultsPlusButton.setOnClickListener {
            var number = Integer.parseInt(adults.text.toString())
            number++
            adults.text = number.toString()

        }

        adultsMinusButton.setOnClickListener {
            var number = Integer.parseInt(adults.text.toString())
            if (number > 0) {
                number--
                adults.text = number.toString()
            }
        }

        minors.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Non necessario implementare
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando il testo cambia, aggiorna il LiveData nel ViewModel
                s?.toString()?.let { text ->
                    val number = if (text.isEmpty()) 0 else text.toInt()
                    ticketsActivityViewModel.setMinorsNumber(number)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Non necessario implementare
            }
        })

        minorsPlusButton.setOnClickListener {
            var number = Integer.parseInt(minors.text.toString())
            number++
            minors.text = number.toString()

        }

        minorsMinusButton.setOnClickListener {
            var number = Integer.parseInt(minors.text.toString())
            if (number > 0) {
                number--
                minors.text = number.toString()

            }

        }

    }


    fun openCalendar(id : Int) {
        val calendar = Calendar.getInstance(TimeZone.GMT_ZONE)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        var test : LocalDate

        val dateCalendarDialog = DatePickerDialog(requireContext(),
            { _, SelectedYear, SelectedMonth, SelectedDay ->

                val selectedDate = LocalDate.of(SelectedYear, SelectedMonth + 1, SelectedDay)
                //Log.d("TAG" , selectedDate.toString())

                /*Formatto la data selezionata nel calendario in */
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy" , Locale.getDefault())
                val formattedDate = selectedDate.format(formatter)

                /*Setto il campo text della EditTExtView con la data presa e formattata dal calendario*/
                val date : EditText  = requireView().findViewById(id)
                date.setText(formattedDate)
                //Log.d("TAG" , date.text.toString())

            }, year,month,day )

        dateCalendarDialog.show()


    }

}