package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.proyectofinal.myapplication.objetos.Categoria

class FavoriteCategoryViewModel (app: Application): AndroidViewModel(app) {
    var favoriteCategory : MutableLiveData<Categoria> =  MutableLiveData()
    fun setNewFavorite (category : Categoria) {
        favoriteCategory.value = category
    }
}