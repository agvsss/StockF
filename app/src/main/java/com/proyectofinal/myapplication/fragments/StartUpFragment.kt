package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.myapplication.R


class StartUpFragment : Fragment() {
    /*
    Acá arriba declaro todas mis variables de Views
     */
    private lateinit var buttonLogIn : Button
    private lateinit var buttonSignUp: Button
    private lateinit var buttonPrueba: Button
    private lateinit var v: View
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_start_up, container, false)
        buttonLogIn = v.findViewById(R.id.buttonLogIn) //Asigno uno
        buttonSignUp = v.findViewById(R.id.buttonSignUp)
        buttonPrueba = v.findViewById(R.id.btn_prueba)
        return v
    }

    override fun onStart() {
        super.onStart()
        buttonSignUp.setOnClickListener{
            val directions = StartUpFragmentDirections.actionStartUpFragmentToSignUpFragment()
            v.findNavController().navigate(directions)
        }

        buttonLogIn.setOnClickListener{
            val directions = StartUpFragmentDirections.actionStartUpFragmentToLogInFragment()
            v.findNavController().navigate(directions)
        }

        buttonPrueba.setOnClickListener(){
            val saltarlogin = StartUpFragmentDirections.actionStartUpFragmentToInventarioFragment()
            v.findNavController().navigate(saltarlogin)
        }

    }
}