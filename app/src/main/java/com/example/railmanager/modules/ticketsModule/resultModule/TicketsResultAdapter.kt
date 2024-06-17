package com.example.railmanager.modules.ticketsModule.resultModule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets
import com.example.railmanager.modules.dbModule.ticketsDbModule.withDefaultValues
import com.example.railmanager.modules.dbModule.trainDbModule.TrainDetailsRequestMethods
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel
import com.google.gson.Gson

class TicketsResultAdapter(private val fragment : Fragment,
                           private val data : ArrayList<Tickets>,
                           ticketsActivityViewModel: TicketsActivityViewModel) : RecyclerView.Adapter<TicketsResultAdapter.ViewHolder>() {

    val trainDetailsRequestMethods = TrainDetailsRequestMethods()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val cardView = view.findViewById<View>(R.id.cardViewRvResultsTicketsFragment)
        val startLocationTextView = view.findViewById<TextView>(R.id.startLocationTextViewRvPaymentTicketsFragment)
        val endLocationTextView = view.findViewById<TextView>(R.id.endLocationTextViewRvPaymentTicketsFragment)
        val departureTimeTextView = view.findViewById<TextView>(R.id.startHourTextViewRvPaymentTicketsFragment)
        val arrivalTimeTextView = view.findViewById<TextView>(R.id.endHourTextViewRvPaymentTicketsFragment)
        val trainTypeTextView = view.findViewById<TextView>(R.id.trainTypeTextViewRvPaymentTicketsFragment)
        val trainClassNumberTextView = view.findViewById<TextView>(R.id.classNumberTextViewRvPaymentTicketsFragment)
        val adultsTicketPriceTextView = view.findViewById<TextView>(R.id.adultsPriceTextViewRvPaymentTicketsFragment)
        val minorsTicketPriceTextView = view.findViewById<TextView>(R.id.minorsPriceTextViewRvPaymentTicketsFragment)
        val availableSeatsTextView = view.findViewById<TextView>(R.id.availableSeatsTextViewRvPaymentTicketsFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_results_tickets_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.departureTimeTextView.text = UsefulStaticMethods.formattaData(item.departure_time)
        holder.arrivalTimeTextView.text = UsefulStaticMethods.formattaData(item.arrival_time)
        holder.trainClassNumberTextView.text = "Numero classe: " + item.class_number.toString()

        // Converto in JSON trainDetails
        val gson = Gson()
        var jsonString : String = ""

        trainDetailsRequestMethods.getTrainDetails(item.train_id) { trainDetails ->
            if (trainDetails != null) {
                holder.startLocationTextView.text = trainDetails.departure_city+","+trainDetails.departure_station
                holder.endLocationTextView.text = trainDetails.arrival_city+","+trainDetails.arrival_station
                holder.trainTypeTextView.text = trainDetails.train_type
                holder.availableSeatsTextView.text = "Posti disponibili: " + trainDetails.available_seats

                jsonString = gson.toJson(trainDetails)


            } else {
                // Gestisci il caso di errore o risposta nulla qui
                Log.d("TrainDetails", "Failed to retrieve train details")
            }
        }

        holder.cardView.setOnClickListener{
            //Sostituisco eventuali valori a null con i valori di default, in quanto ticket è serializable e non può avere valori null
            val ticketsWithDefault = item.withDefaultValues()
            // Naviga utilizzando Safe Args
            fragment.findNavController().navigate(TicketsResultFragmentDirections.actionTicketsResultFragmentToTicketsPaymentFragment(ticketsWithDefault, jsonString) )
        }

        holder.adultsTicketPriceTextView.text = "Prezzo adulti: "+ item.adults_price.toString()
        holder.minorsTicketPriceTextView.text = "Prezzo minori: "+ item.minors_price.toString()

    }


}