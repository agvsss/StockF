package com.proyectofinal.myapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.myapplication.R

class LogInFragment : Fragment() {
    private lateinit var v: View
    private lateinit var logInButton: Button
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextMail: TextInputEditText
    private val TAG = "LogInFragment"
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_log_in, container, false)
        editTextMail = v.findViewById(R.id.eInput)
        editTextPassword = v.findViewById(R.id.passwordInput)
        logInButton = v.findViewById(R.id.confirmDataLogInButton)
        auth=FirebaseAuth.getInstance()
        return v
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser==null)  
        {
            val directions = LogInFragmentDirections.actionLogInFragmentToInventarioFragment()
            v.findNavController().navigate(directions)
        }
        else
        {
            logInButton()
            //TODO: IR A LA PANTALLA PRINCIPAL DE LA APP
        }
    }

    private fun logInButton() {
        logInButton.setOnClickListener {
            val mailStr = editTextMail.text.toString().trim()
            val passStr = editTextPassword.text.toString().trim()
            Log.d(TAG, "Mail is $mailStr and pass is $passStr")
            if (mailStr.isNotEmpty() && passStr.isNotEmpty()) {
                Log.d(TAG, "Mail is $mailStr and pass is $passStr")
                auth.signInWithEmailAndPassword(mailStr, passStr)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val action3to1= LogInFragmentDirections.actionLogInFragmentToInventarioFragment()
                            v.findNavController().navigate(action3to1)
                            //TODO: IR A LA PANTALLA PRINCIPAL DE LA APP
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Snackbar
                                .make(v, "No se pudo inciar sesion", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }

            } else {
                Snackbar
                    .make(v, "Por favor completa los campos", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}