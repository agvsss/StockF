package com.proyectofinal.myapplication.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.proyectofinal.myapplication.R
import viewmodels.FavoriteCategoryViewModel


class InventarioFragment : Fragment() {

    private lateinit var v: View
    private val favoriteCategoryViewModel by activityViewModels<FavoriteCategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_inventario2, container, false)
        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return v
    }

    override fun onStart() {
        super.onStart()
        val estrella : TextView = v.findViewById(R.id.textView2)
        estrella.text = favoriteCategoryViewModel.favoriteCategory.value?.nombre
    }
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.inventario_toolbar, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            val id = when(item.itemId) {

                R.id.action_logout -> {
                  //  FirebaseAuth.getInstance().signOut()
                    val action3to1= InventarioFragmentDirections.actionInventarioFragmentToLogInFragment()
                    v.findNavController().navigate(action3to1)

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

