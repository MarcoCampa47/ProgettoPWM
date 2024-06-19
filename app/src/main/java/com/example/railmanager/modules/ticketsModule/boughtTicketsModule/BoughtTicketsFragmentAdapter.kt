package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.boughtDbModule.BoughtTicketsRequest
import com.example.railmanager.modules.dbModule.ticketsDbModule.Tickets

class BoughtTicketsFragmentAdapter(fragment: Fragment , val data : List<BoughtTicketsRequest>) : RecyclerView.Adapter<BoughtTicketsFragmentAdapter.ViewHolder>() {

    val fragment = fragment;

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val cardView = view.findViewById<View>(R.id.cardViewItemRvBoughtTicketsFragment)
        val startLocationTextView = view.findViewById<TextView>(R.id.startLocationTextViewRvBoughtTicketsFragment)
        val endLocationTextView = view.findViewById<TextView>(R.id.endLocationTextViewRvBoughtTicketsFragment)
        val dataPurchaseTextView = view.findViewById<TextView>(R.id.BoughtDateTextViewRvBoughtTicketsFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_bought_tickets_fragment,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.cardView.setOnClickListener {
            fragment.findNavController().navigate(BoughtTicketsFragmentDirections.actionBoughtTicketsFragmentToBoughtTicketsDetailFragment(item))
        }
        holder.startLocationTextView.text = item.departure_city_name + ", " + item.departure_station_name
        holder.endLocationTextView.text = item.arrival_city_name + ", " + item.arrival_station_name
        holder.dataPurchaseTextView.text =  UsefulStaticMethods.formattaData(item.date_purchase)

    }
}