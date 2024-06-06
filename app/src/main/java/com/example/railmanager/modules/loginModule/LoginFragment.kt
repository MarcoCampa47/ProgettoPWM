package com.example.railmanager.modules.loginModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.railmanager.R
import com.example.railmanager.modules.registrationModule.RegistrationFragment

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val viewWithfragment = inflater.inflate(R.layout.loginfragment_layout, container, false)

        val registerButton = viewWithfragment.findViewById<Button>(R.id.notRegisteredButtonLoginFragment)
        registerButton.setOnClickListener(){
            val loginFragmentManager = requireActivity().supportFragmentManager
            val loginFragmentTransaction = loginFragmentManager.commit {
                setReorderingAllowed(true)
                /*Aggiungo al backstack la transazione (il nome è a piacere), così da poter tornare alla schermata precedente col tasto indietro del sistema operativo*/
                addToBackStack("move_to_register_form")
                setCustomAnimations(    com.google.android.material.R.anim.m3_side_sheet_enter_from_right,
                    com.google.android.material.R.anim.m3_side_sheet_exit_to_left,
                    com.google.android.material.R.anim.m3_side_sheet_enter_from_left,
                    com.google.android.material.R.anim.m3_side_sheet_exit_to_right)
                replace<RegistrationFragment>(R.id.fragmentContainerActivityMain)
            }
        }

        return viewWithfragment;
    }

}