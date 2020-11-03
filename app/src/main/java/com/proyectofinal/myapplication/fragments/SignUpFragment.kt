package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.objetos.Usuario
import org.w3c.dom.Text


class   SignUpFragment : Fragment() {
    private lateinit var v: View
    private lateinit var buttonSignUp: Button
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private val TAG = "SignUpFragment"
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_sign_up, container, false)
        buttonSignUp = v.findViewById(R.id.confirmDataSignUpButton)
        editTextEmail = v.findViewById(R.id.newEmailInput)
        editTextName = v.findViewById(R.id.newUserNameInput)
        editTextPassword = v.findViewById(R.id.newPasswordInput)

        auth = FirebaseAuth.getInstance()

        return v
    }

    override fun onStart() {
        super.onStart()
        signUpButton()
    }

    private fun signUpButton() {
        buttonSignUp.setOnClickListener {
            val emailStr: String = editTextEmail.text.toString().trim()
            val passStr: String = editTextPassword.text.toString().trim()
            val nameStr: String = editTextName.text.toString().trim()
            if (emailStr.isNotEmpty() && passStr.isNotEmpty() && nameStr.isNotEmpty()) {
                Log.d(TAG, "User will be created $emailStr, $passStr, $nameStr")
                auth.createUserWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            var usuario = Usuario(nameStr,emailStr,"",ArrayList())
                            db.collection("users").document(usuario.email).set(usuario)

                            val action3to1= SignUpFragmentDirections.actionSignUpFragmentToInventarioFragment()
                            v.findNavController().navigate(action3to1)
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Snackbar
                                .make(v, "No se pudo crear tu usuario", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Log.w(TAG, "createUserWithEmail:NoConfirmaRegistro")
                Snackbar
                    .make(v, "Por favor completa los campos", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
