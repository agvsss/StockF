package com.proyectofinal.myapplication.objetos

import viewmodels.FavoriteCategoryViewModel

class Usuario(var name: String, var favoriteCategoryViewModel: FavoriteCategoryViewModel?) {

    constructor():this("",null)
}