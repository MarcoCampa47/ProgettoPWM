package com.example.railmanager.modules.ticketsModule

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.railmanager.R
import com.example.railmanager.modules.dbModule.UsefulStaticMethods

class TicketsActivity : AppCompatActivity() {

    val ticketsActivityViewModel : TicketsActivityViewModel by viewModels()
    
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


        val userInfoImageView = findViewById<ImageView>(R.id.profileSectionImageViewActivityTickets)
        userInfoImageView.setOnClickListener {
            val navController = findNavController(R.id.navHostActivityTickets)
            navController.navigate(R.id.boughtTicketsFragment)
        }

        val searchImageView = findViewById<ImageView>(R.id.searchImageViewActivityTickets)
        searchImageView.setOnClickListener {
            val navController = findNavController(R.id.navHostActivityTickets)
            navController.navigate(R.id.trainRoutesFragment)
        }






    }


    /* Se lascio l'app in background, ritorna all'activity TicketsActivity */
    override fun onResume() {
        super.onResume()
        if (!isTaskRoot) {
            finish()
        }
    }

}

