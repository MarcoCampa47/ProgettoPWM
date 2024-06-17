package com.example.railmanager.modules.ticketsModule

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods
import com.example.railmanager.modules.ticketsModule.searchModule.TrainRoutesFragmentViewModel

class TicketsActivity : AppCompatActivity() {

    val ticketsActivityViewModel : TicketsActivityViewModel by viewModels()
    private var emailUtente : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent;

        ticketsActivityViewModel.setEmail( intent.getStringExtra("e-mail")!!)
        ticketsActivityViewModel.setIdUtente(intent.getIntExtra("idUtente",-1))

        if(ticketsActivityViewModel.getIdUtente() == -1){
            UsefulStaticMethods.showSimpleAlertDialog(this, "Errore nella fase di login")
            finish()
        }

        Log.d("idutente", ticketsActivityViewModel.getIdUtente().toString())

        //N.B PER TEST, CANCELLARE DOPO
        emailUtente = "mmarkcampy@gmail.com"







    }


    /* Se lascio l'app in background, ritorna all'activity TicketsActivity */
    override fun onResume() {
        super.onResume()
        if (!isTaskRoot) {
            finish()
        }
    }

}

