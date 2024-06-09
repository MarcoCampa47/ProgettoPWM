package com.example.railmanager.modules.registrationModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.railmanager.R
import com.example.railmanager.modules.loginModule.LoginFragment


class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewWithfragment = inflater.inflate(R.layout.registrationfragment_layout, container, false)
        val goBackButton = viewWithfragment.findViewById<ImageButton>(R.id.goBackImageButtonRegistrationFragment)


        goBackButton.setOnClickListener(){
            val registerFragmentManager = requireActivity().supportFragmentManager

            val registerFragmentTransaction = registerFragmentManager.commit {
                setReorderingAllowed(true)
                /*Aggiungo al backstack la transazione (il nome è a piacere), così da poter tornare alla schermata precedente col tasto indietro del sistema operativo*/
                addToBackStack("move_to_login_form")
                setCustomAnimations(  com.google.android.material.R.anim.m3_side_sheet_enter_from_left, // Il nuovo Fragment entra da sinistra
                    com.google.android.material.R.anim.m3_side_sheet_exit_to_right,   // Il vecchio Fragment esce a destra
                    com.google.android.material.R.anim.m3_side_sheet_enter_from_right, // Se torni indietro, il vecchio Fragment entra da destra
                    com.google.android.material.R.anim.m3_side_sheet_exit_to_left )
                replace<LoginFragment>(R.id.fragmentContainerActivityMain)
                addToBackStack(null)
            }
        }

        return viewWithfragment
    }

}