package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.data.Meal
import pw.prsk.goodfood.repository.MealCategoryRepository
import pw.prsk.goodfood.repository.MealRepository
import pw.prsk.goodfood.utils.SingleLiveEvent
import javax.inject.Inject


class MealsViewModel : ViewModel(), ItemTouchHelperAction {
    val mealList: MutableLiveData<List<Meal>> by lazy {
        MutableLiveData<List<Meal>>()
    }

    val deleteSnack = SingleLiveEvent<String>()
    var test = "TESTIM"

    @Inject lateinit var mealRepository: MealRepository
    @Inject lateinit var mealCategoryRepository: MealCategoryRepository

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.addMeal(meal)
            loadMealsList()
        }
    }

    fun loadMealsList() {
        viewModelScope.launch {
            mealList.postValue(mealRepository.getMeals())
        }
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModelScope.launch {
            val item = mealList.value?.get(position)
            deleteSnack.value = item?.name // Show snackbar with deleted item name
            mealRepository.removeMeal(item!!)
            loadMealsList()
        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}