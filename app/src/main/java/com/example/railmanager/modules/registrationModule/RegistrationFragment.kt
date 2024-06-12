package com.example.railmanager.modules.registrationModule

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.railmanager.R
import com.example.railmanager.modules.loginModule.LoginFragment
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


class RegistrationFragment : Fragment() {

    val registrationFragmentViewModel : RegistrationFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date : EditText  = view.findViewById(R.id.dateEditableTextRegistrationFragment)
        val email : EditText = view.findViewById(R.id.emailEditableTextRegistrationFragment)
        val password : EditText = view.findViewById(R.id.passwordEditableTextRegistrationFragment)
        val name : EditText = view.findViewById(R.id.nameEditableTextRegistrationFragment)
        val surname : EditText = view.findViewById(R.id.surnameEditableTextRegistrationFragment)


        date.showSoftInputOnFocus = false
        date.setOnClickListener {
            openCalendar()

        }

        view.findViewById<Button>(R.id.registerButtonRegistrationFragment).setOnClickListener {
            if(isValidNameAndSurname(name.text.toString() , surname.text.toString())) {
                Toast.makeText(context, "Email valida", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Email non valida", Toast.LENGTH_SHORT).show()
            }

        }



    }


    /*Metodo che verifica la corretta formattazione dell'indirizzo email*/
    fun isValidMail(email : String) : Boolean{
        val emailRegex = Regex("^[a-zA-Z][a-zA-Z0-9._-]*@(gmail\\.com|outlook\\.com|yahoo\\.com|icloud\\.com|virgilio\\.it|libero\\.it|aruba\\.it|protonmail\\.com|namirial\\.com)\$")

        return emailRegex.matches(email)
    }

    /*Verifica la corretta formattazione della password*/
    fun isValidPassword(password : String) : Boolean{
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[!;#%&()*?@\\[\\]\\\\^\\-_{}])[A-Za-z0-9!;#%&()*?@\\[\\]\\\\^\\-_{}]{8,}$")
        return passwordRegex.matches(password)
    }

    /*Verifica la corretta formattazione del nome e del cognome*/
    fun isValidNameAndSurname(name : String , surname: String) : Boolean{
        val nameAndSurnameRegex = Regex("^[a-zA-Z]{2,}\$")

        return nameAndSurnameRegex.matches(name) && nameAndSurnameRegex.matches(surname)
    }

    /*Metodo per verificare che la data di nascita rispetti i criteri prestabiliti*/

    fun isValidDate(date: String): Int? {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = LocalDate.parse(date, formatter)
            val currentDate = LocalDate.now()
            val yearsBetween = Period.between(selectedDate, currentDate).years
            Log.d("TAG1", "Selected Date: $selectedDate, Current Date: $currentDate, Years Between: $yearsBetween")
            yearsBetween
        } catch (e: DateTimeParseException) {
            Log.d("TAG1", "Invalid date format")
            null
        }
    }

    /*Metodo per verificare che il numero di telefono sia correttamente formattato*/
    fun isValidPhoneNumber(phone : String) : Boolean{
        val phoneRegex = Regex("^\\+[0-9]{1,3}[0-9]{10}\$")
        return phoneRegex.matches(phone)
    }

    /*Metodo per l'apertura di un calendario*/
    fun openCalendar() {
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
                val date : EditText  = requireView().findViewById(R.id.dateEditableTextRegistrationFragment)
                date.setText(formattedDate)
                //Log.d("TAG" , date.text.toString())

            }, year,month,day )

        dateCalendarDialog.show()


    }



}