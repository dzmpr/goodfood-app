package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.prsk.goodfood.data.IngredientWithMeta

class EditMealViewModel : ViewModel() {
    private val ingredientsList: MutableList<IngredientWithMeta> = mutableListOf()

    val ingredients: MutableLiveData<List<IngredientWithMeta>> = MutableLiveData<List<IngredientWithMeta>>(ingredientsList)

//    init {
//        with(ingredientsList) {
//            add(IngredientWithMeta(0,"Cucumber", 10.0f,0, "pcs."))
//            add(IngredientWithMeta(0,"Tomato", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Potato", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Grape", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Lemon", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Eggplant", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Onion", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Banana", 10.0f, 0,"pcs."))
//            add(IngredientWithMeta(0,"Orange", 10.0f, 0,"pcs."))
//        }
//
//        ingredients.value = ingredientsList
//    }

    fun addIngredient(item: IngredientWithMeta) {
        ingredientsList.add(item)
        ingredients.value = ingredientsList
    }
}