package com.example.railmanager.modules.loginModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.railmanager.R
import com.example.railmanager.modules.registrationModule.RegistrationFragment

class LoginFragment : Fragment() {

    val loginFragmentViewModel : LoginFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val viewWithfragment = inflater.inflate(R.layout.loginfragment_layout, container, false)

        val registerButton = viewWithfragment.findViewById<Button>(R.id.notRegisteredButtonLoginFragment)
        registerButton.setOnClickListener(){
            val loginFragmentManager = requireActivity().supportFragmentManager
            // Pulisce tutto il back stack
            if (loginFragmentManager.backStackEntryCount > 0) {
                val first = loginFragmentManager.getBackStackEntryAt(0)
                loginFragmentManager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
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

        val emailEditText = viewWithfragment.findViewById<EditText>(R.id.emailEditTextLoginFragment)
        val passwordEditText = viewWithfragment.findViewById<EditText>(R.id.passwordEditTextLoginFragment)
        val loginButton = viewWithfragment.findViewById<Button>(R.id.loginButtonLoginFragment)
        loginButton.setOnClickListener(){
            this.context?.let { it1 -> loginFragmentViewModel.login(it1, emailEditText.text.toString().trim(), passwordEditText.text.toString().trim() ) }
        }


        return viewWithfragment;
    }

}