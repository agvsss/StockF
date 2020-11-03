package com.proyectofinal.myapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.adapters.CategoriaListAdapter
import com.proyectofinal.myapplication.objetos.Usuario

class InventarioFragment : Fragment() {

    private lateinit var v: View

    private lateinit var tv_categoria: TextView
    private lateinit var btn_RightArrow: ImageButton
    private lateinit var btn_LeftArrow: ImageButton

    private lateinit var usuario: Usuario

    private var categorias: MutableList<String> = ArrayList()
    private var position: Int = 0

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_inventario2, container, false)
        tv_categoria = v.findViewById(R.id.tv_categoria)
        btn_RightArrow = v.findViewById(R.id.RightArrow)
        btn_LeftArrow = v.findViewById(R.id.LeftArrow)
        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
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
                    usuario.categorias.remove(usuario.favoriteCategorie)

                    categorias.add(usuario.favoriteCategorie)
                    for (categoria in usuario.categorias) {
                        categorias.add(categoria)
                    }

                    tv_categoria.text = categorias[position]

                    btn_RightArrow.setOnClickListener {
                        if (position == categorias.size-1) {
                            position = 0
                        }
                        else {
                            position++
                        }
                        Log.d("Test","position: $position")
                        tv_categoria.text = categorias[position]
                    }
                    btn_LeftArrow.setOnClickListener {
                        if (position == 0) {
                            position = categorias.size-1
                        }
                        else {
                            position--
                        }
                        Log.d("Test","position: $position")
                        tv_categoria.text = categorias[position]
                    }
                } else {
                    Log.d("Test", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Test", "get failed with ", exception)
            }
    }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.inventario_toolbar, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            val id = when(item.itemId) {

                R.id.action_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val sharedPref: SharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    val action1to3= InventarioFragmentDirections.actionInventarioFragmentToStartUpFragment()
                    v.findNavController().navigate(action1to3)
                }

                R.id.action_add -> {
                    val action1to2= InventarioFragmentDirections.actionInventarioFragmentToAddProductFragment()
                    v.findNavController().navigate(action1to2)
                }

                R.id.action_configuracion ->{
                    val action1to4 = InventarioFragmentDirections.actionInventarioFragmentToConfiguracionFragment()
                    v.findNavController().navigate(action1to4)
                }

                else -> ""
            }
            return super.onOptionsItemSelected(item)
        }

}

