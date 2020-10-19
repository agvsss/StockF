package com.proyectofinal.myapplication.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.proyectofinal.myapplication.R
import com.proyectofinal.myapplication.objetos.Categoria

    class CategoriaListAdapter(
        private var categoriaList: MutableList<Categoria>,
        var context: Context,
        val onItemClick: (Int) -> Unit,
        val deleteCategory: (Categoria) -> Unit,
        val addFavorite: (Categoria) -> Unit
    ) : RecyclerView.Adapter<CategoriaListAdapter.CategoriaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_categoria,parent,false)
        return (CategoriaHolder(
            view
        ))
    }

    override fun getItemCount(): Int {

        return categoriaList.size
    }

    class CategoriaHolder (v: View) : RecyclerView.ViewHolder(v){

        private var view : View

        init {
            this.view = v
        }
        fun bindItems(
            model: Categoria,
            deleteCategory: (Categoria) -> Unit,
            addFavorite: (Categoria) -> Unit
        ) {
            val txt : TextView = view.findViewById(R.id.txt_name_item)
            txt.text = model.nombre
            var btnEliminar : ImageButton = view.findViewById(R.id.btn_eliminar)
            btnEliminar.setOnClickListener {
                deleteCategory(model)
            }
            var btnFavorito : ImageButton = view.findViewById(R.id.btn_favoritos)
            btnFavorito.setOnClickListener {
                try {
                    addFavorite(model)
                }
                catch (e: Exception) {
                    Log.d(TAG, "hola")
                }

            }
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_item)
        }
    }

        override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
            holder.bindItems(categoriaList[position], deleteCategory,addFavorite)
            holder.getCardLayout().setOnClickListener {
                onItemClick(position)
            }
        }
    }



