package pw.prsk.goodfood.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pw.prsk.goodfood.data.RecipeWithMeta
import pw.prsk.goodfood.repository.RecipeRepository
import pw.prsk.goodfood.utils.ItemTouchHelperAction
import pw.prsk.goodfood.utils.SingleLiveEvent
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel(), ItemTouchHelperAction {
    private val recipes = MutableLiveData<List<RecipeWithMeta>>()
    val recipeList: LiveData<List<RecipeWithMeta>>
        get() = recipes

    init {
        viewModelScope.launch {
            recipeRepository.getAllRecipes()
                .onEach { recipes.postValue(it) }
                .launchIn(this)
        }
    }

    val deleteSnack = SingleLiveEvent<String>()

    override fun itemSwiped(position: Int, direction: Int) {
//        viewModelScope.launch {
//            val item = recipe.value?.get(position)
//            deleteSnack.value = item?.name // Show snackbar with deleted item name
//            productRepository.removeById(item!!.id!!)
//        }
    }

    override fun itemMoved(startPosition: Int, endPosition: Int) {}
}