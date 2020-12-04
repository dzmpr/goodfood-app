package pw.prsk.goodfood.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.data.Meal
import pw.prsk.goodfood.repository.MealCategoryRepository
import pw.prsk.goodfood.repository.MealRepository


class MealsViewModel : ViewModel(), ItemTouchHelperAction {
    val mealList: MutableLiveData<List<Meal>> by lazy {
        MutableLiveData<List<Meal>>()
    }

    private var mealRepository: MealRepository? = null
    private var mealCategoryRepository: MealCategoryRepository? = null

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository!!.addMeal(meal)
            loadMealsList()
        }
    }

    private fun loadMealsList() {
        viewModelScope.launch {
            mealList.postValue(mealRepository!!.getMeals())
        }
    }

    fun injectRepositories(mealRepository: MealRepository, mealCategoryRepository: MealCategoryRepository) {
        this.mealRepository = mealRepository
        this.mealCategoryRepository = mealCategoryRepository
        loadMealsList()
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = mealList.value?.get(position)
            mealRepository?.removeMeal(item!!)
            loadMealsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}

    override fun onCleared() {
        mealRepository = null
        mealCategoryRepository = null
    }
}