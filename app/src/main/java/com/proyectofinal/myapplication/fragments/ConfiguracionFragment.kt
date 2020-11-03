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
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.adapters.CategoriaListAdapter
import com.proyectofinal.myapplication.objetos.Usuario
import java.util.*
import kotlin.collections.ArrayList

class ConfiguracionFragment : Fragment () {

    private lateinit var v: View

    private lateinit var btn_add_categoria: Button

    lateinit var recCategorias : RecyclerView

    private lateinit var usuario: Usuario

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var categoriaListAdapter : CategoriaListAdapter
    private lateinit var categoria : String
    private lateinit var et_categoria : EditText

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_configuracion, container, false)
        btn_add_categoria = v.findViewById(R.id.btn_add_categoria)
        recCategorias = v.findViewById(R.id.rec_categorias)
        et_categoria = v.findViewById(R.id.et_categoria)
        return v
    }

    override fun onStart() {
        super.onStart()
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        var usermail = sharedPref.getString("USER", "default")!!

        var docRef = db.collection("users")
        docRef.whereEqualTo("email", usermail).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var userDataSnapshot = dataSnapshot.documents
                    usuario = userDataSnapshot[0].toObject<Usuario>()!!

                    recCategorias.setHasFixedSize(true)

                    linearLayoutManager = LinearLayoutManager(context)
                    recCategorias.layoutManager = linearLayoutManager

                    categoriaListAdapter = CategoriaListAdapter(usuario.categorias, requireContext(), {position -> deleteCategory(position)}, {position -> addFavorite(position)})

                    recCategorias.adapter = categoriaListAdapter

                    btn_add_categoria.setOnClickListener(){
                        if (et_categoria.text.isNotEmpty()) {
                            categoria = et_categoria.text.toString()

                            usuario.categorias.add(categoria)

                            db.collection("users").document(usuario.email).set(usuario)

                            recCategorias.setHasFixedSize(true)

                            linearLayoutManager = LinearLayoutManager(context)
                            recCategorias.layoutManager = linearLayoutManager

                            categoriaListAdapter = CategoriaListAdapter(usuario.categorias, requireContext(), {position -> deleteCategory(position)}, {position -> addFavorite(position)})

                            recCategorias.adapter = categoriaListAdapter
                        }
                        else {
                            Snackbar.make(v,"Por favor completa los campos", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }
    }

    fun deleteCategory(position: Int) {
        usuario.categorias.remove(usuario.categorias[position])
        db.collection("users").document(usuario.email).set(usuario)

        recCategorias.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        recCategorias.layoutManager = linearLayoutManager

        categoriaListAdapter = CategoriaListAdapter(usuario.categorias, requireContext(), {position -> deleteCategory(position)}, {position -> addFavorite(position)})

        recCategorias.adapter = categoriaListAdapter
    }

    fun addFavorite(position: Int) {
        usuario.favoriteCategorie = usuario.categorias[position]
        db.collection("users").document(usuario.email).set(usuario)
        Snackbar.make(v,"Se añadio como categoria favorita", Snackbar.LENGTH_SHORT).show()
    }
}