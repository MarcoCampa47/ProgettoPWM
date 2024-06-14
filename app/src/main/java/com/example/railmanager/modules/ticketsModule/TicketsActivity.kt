package com.example.railmanager.modules.ticketsModule

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.railmanager.R
import com.example.railmanager.modules.ticketsModule.searchModule.TrainRoutesFragmentViewModel

class TicketsActivity : AppCompatActivity() {
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
        val emailUtente = intent.getStringExtra("e-mail")

        /*
        val trainRoutesFragmentViewModel = emailUtente?.let { TrainRoutesFragmentViewModel(it) }
        if (emailUtente != null) {
            Log.d("DEBUG", emailUtente)
        }
        */

    }


    /* Se lascio l'app in background, ritorna all'activity TicketsActivity */
    override fun onResume() {
        super.onResume()
        if (!isTaskRoot) {
            finish()
        }
    }
}

