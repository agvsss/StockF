package com.proyectofinal.myapplication.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.proyectofinal.myapplication.R

class CategoriaListAdapter(private var categoriaList: MutableList<String>, var context: Context, val deleteCategory: (Int) -> Unit, val addFavorite: (Int) -> Unit) : RecyclerView.Adapter<CategoriaListAdapter.CategoriaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_categoria,parent,false)
        return (CategoriaHolder(
            view
        ))
    }

    override fun getItemCount(): Int {
        return categoriaList.size
    }

    fun setData(newData: ArrayList<String>) {
        this.categoriaList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        holder.setName(categoriaList[position])
        holder.getDeleteButton().setOnClickListener {
            deleteCategory(position)
        }
        holder.getFavoriteButton().setOnClickListener {
            addFavorite(position)
        }
    }

    class CategoriaHolder (v: View) : RecyclerView.ViewHolder(v){

        private var view : View

        init {
            this.view = v
        }

        fun setName(name : String) {
            val txt : TextView = view.findViewById(R.id.txt_name_item)
            txt.text = name
        }

        fun getDeleteButton (): ImageButton {
            return view.findViewById(R.id.btn_eliminar)
        }

        fun getFavoriteButton (): ImageButton {
            return view.findViewById(R.id.btn_favoritos)
        }
    }
}