package com.example.railmanager.modules.ticketsModule.boughtTicketsModule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.dbModule.boughtDbModule.BoughtTicketsRequest
import com.example.railmanager.modules.ticketsModule.TicketsActivityViewModel


class BoughtTicketsFragment : Fragment() {

    val ticketsActivityViewModel : TicketsActivityViewModel by viewModels()
    val boughtTicketsFragmentViewModel : BoughtTicketsFragmentViewModel by viewModels()
    //val boughtTicketsFragmentAdapter = BoughtTicketsFragmentAdapter(this , )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bought_tickets, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        this.context?.let { boughtTicketsFragmentViewModel.getBoughtTickets(it, ticketsActivityViewModel.getIdUtente() ) {
            listObtained -> if(listObtained.isNotEmpty()){
                            val boughtTicketsFragmentAdapter = BoughtTicketsFragmentAdapter(this , listObtained)
                            val recyclerView : RecyclerView = view.findViewById(R.id.rvBoughtTicketsFragment)

                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.adapter = boughtTicketsFragmentAdapter
                            }
                            else{
                                this.context?.let { UsefulStaticMethods.showSimpleAlertDialog(it, "Non hai acquistato alcun biglietto") }
                            }
            }
        }


    }



}