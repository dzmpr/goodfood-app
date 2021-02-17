package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.prsk.goodfood.data.Ingredient
import pw.prsk.goodfood.data.ProductUnit

class EditMealViewModel : ViewModel() {
    private val ingredientsList: MutableList<Ingredient> = mutableListOf()

    val ingredients: MutableLiveData<List<Ingredient>> = MutableLiveData<List<Ingredient>>(ingredientsList)

    init {
        with(ingredientsList) {
            add(Ingredient("Cucumber", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Tomato", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Potato", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Grape", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Lemon", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Eggplant", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Onion", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Banana", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Orange", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Cucumber", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Tomato", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Potato", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Grape", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Lemon", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Eggplant", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Onion", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Banana", 10.0f, ProductUnit(name = "pcs.")))
            add(Ingredient("Orange", 10.0f, ProductUnit(name = "pcs.")))
        }

        ingredients.value = ingredientsList
    }
}