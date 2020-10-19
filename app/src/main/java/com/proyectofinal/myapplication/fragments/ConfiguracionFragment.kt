package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.adapters.CategoriaListAdapter
import com.proyectofinal.myapplication.objetos.Categoria
import viewmodels.FavoriteCategoryViewModel
import java.util.*
import kotlin.collections.ArrayList

class ConfiguracionFragment : Fragment () {

    private lateinit var v: View

    private lateinit var btn_add_categoria: Button


    lateinit var recCategorias : RecyclerView

    var categorias : MutableList <Categoria> = ArrayList <Categoria> ()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var categoriaListAdapter : CategoriaListAdapter
    private lateinit var nombre_categoria : String
    private lateinit var et_categoria : EditText

    private val db = Firebase.firestore
    private val favoriteCategoryViewModel by activityViewModels<FavoriteCategoryViewModel>()
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
        favoriteCategoryViewModel.favoriteCategory.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d("Hey!",it.toString())
        }

        )
        var docRef = db.collection("categorias")
        docRef.get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot != null) {
                    var mueblesDataSnapshot = dataSnapshot.documents
                    for (snapshot in mueblesDataSnapshot) {
                        snapshot.toObject<Categoria>()?.let { categorias.add(it) }
                    }
                    recCategorias.setHasFixedSize(true)

                    linearLayoutManager = LinearLayoutManager(context)
                    recCategorias.layoutManager = linearLayoutManager

                    categoriaListAdapter = CategoriaListAdapter(
                        categorias,
                        requireContext(),
                        {position -> onItemClick(position)},
                        {model -> deleteCategory(model)},
                        {model -> addFavorite(model)}
                    )


                    recCategorias.adapter = categoriaListAdapter
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }

        btn_add_categoria.setOnClickListener(){

            if (et_categoria.length() > 0) {
                nombre_categoria = et_categoria.text.toString()
                val uid = UUID.randomUUID().toString()
                var categoria = Categoria(uid,nombre_categoria)
                db.collection("categorias").document(uid).set(categoria)
            }
            else {
                Snackbar.make(v,"Por favor completa los campos", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    fun onItemClick (position : Int) {
        Snackbar.make(v, "Categoria" + (position+1) , Snackbar.LENGTH_SHORT).show()
    }
    fun deleteCategory(model: Categoria) {
        if(categorias.contains(model)) {
            categorias.remove(model)
            db.collection("Categoria").document(model.uid).delete()
        }
    }
    fun addFavorite(model: Categoria) {
        favoriteCategoryViewModel.setNewFavorite(model)
        db.collection("CategoriaFavorita").document(FirebaseAuth.getInstance().currentUser!!.uid).set(model)
    }
}