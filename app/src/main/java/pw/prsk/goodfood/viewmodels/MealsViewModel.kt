package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.Meal
import pw.prsk.goodfood.repository.MealRepository


class MealsViewModel : ViewModel() {
    val mealList: MutableLiveData<List<Meal>> by lazy {
        MutableLiveData<List<Meal>>()
    }

    private var repository: MealRepository? = null

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            repository!!.addMeal(meal)
        }
    }

    fun loadMealList() {
        viewModelScope.launch {
            mealList.postValue(repository!!.getMeals())
        }
    }

    fun injectRepository(repository: MealRepository) {
        this.repository = repository
        loadMealList()
    }

    override fun onCleared() {
        repository = null
    }
}