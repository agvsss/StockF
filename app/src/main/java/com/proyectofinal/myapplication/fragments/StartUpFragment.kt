package com.proyectofinal.myapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.myapplication.R


class StartUpFragment : Fragment() {
    /*
    Acá arriba declaro todas mis variables de Views
     */
    private lateinit var buttonLogIn : Button
    private lateinit var buttonSignUp: Button
    private lateinit var v: View
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_start_up, container, false)
        buttonLogIn = v.findViewById(R.id.buttonLogIn) //Asigno uno
        buttonSignUp = v.findViewById(R.id.buttonSignUp)
        auth = FirebaseAuth.getInstance()
        return v
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null) {
            val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("USER", auth.currentUser?.email)
            editor.apply()

            val action3to1= StartUpFragmentDirections.actionStartUpFragmentToInventarioFragment()
            v.findNavController().navigate(action3to1)
        }

        buttonSignUp.setOnClickListener{
            val directions = StartUpFragmentDirections.actionStartUpFragmentToSignUpFragment()
            v.findNavController().navigate(directions)
        }

        buttonLogIn.setOnClickListener{
            val directions = StartUpFragmentDirections.actionStartUpFragmentToLogInFragment()
            v.findNavController().navigate(directions)
        }
    }
}