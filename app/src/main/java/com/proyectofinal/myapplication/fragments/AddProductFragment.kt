package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.adapters.CategoriaListAdapter
import com.proyectofinal.myapplication.objetos.Categoria
import kotlinx.android.synthetic.main.fragment_add_product.*

class AddProductFragment : Fragment() {

    lateinit var v : View
    lateinit var txtProductName : EditText
    lateinit var txtUnidad : EditText
    lateinit var txtCategoria : TextView
    lateinit var txtFechaVencimiento : EditText

    lateinit var recCategorias : RecyclerView

    var categorias : MutableList <Categoria> = ArrayList <Categoria> ()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var categoriaListAdapter : CategoriaListAdapter

    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add_product, container, false)
    /*    txtProductName = v.findViewById(R.id.textProductName)
        txtUnidad = v.findViewById(R.id.textUnidad)
        txtCategoria = v.findViewById(R.id.text_categoria)
        txtFechaVencimiento = v.findViewById(R.id.textFechaVencimiento) */


        return v
    }

  /*  override fun onStart() {
        super.onStart()

        val items = listOf("Heladera", "bowl de frutas")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        (text_categoria as? AutoCompleteTextView)?.setAdapter(adapter)

                    var listaCategorias: ArrayList<String> = arrayListOf()
                    categorias.forEach { it ->
                        listaCategorias.add(it.nombre)
                    }

    }

    fun onItemClick (position : Int) {
        txtCategoria.text = categorias[position].nombre
    }

   */
}