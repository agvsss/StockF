package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.myapplication.R
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
        return v
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            signUpButton()
        } else {
            //TODO: IR A LA PANTALLA PRINCIPAL DE LA APP
        }
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth?.currentUser
                            val userMap = hashMapOf(
                                "name" to nameStr, "email" to emailStr
                            )
                            user?.uid?.let { it1 ->
                                db.collection("users").document(it1)
                                    .set(userMap)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }
                            }
                            db.collection("users")
                                .get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        Log.d(TAG, "${document.id} => ${document.data}")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(TAG, "Error getting documents.", exception)
                                }
                        } else {
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
