package pw.prsk.goodfood.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory
import pw.prsk.goodfood.data.repository.RecipeCategoryRepository
import javax.inject.Inject

class ManageCategoriesViewModel @Inject constructor(
    private val recipeCategoryRepository: RecipeCategoryRepository
) : ViewModel() {
    private val categories by lazy {
        MutableLiveData<List<RecipeCategory>>()
    }
    val categoryList: LiveData<List<RecipeCategory>>
        get() = categories

    init {
        viewModelScope.launch {
            recipeCategoryRepository.getCategoriesFlow()
                .onEach {
                    categories.value = it
                }
                .launchIn(this)
        }
    }

    fun onCategoryRename(categoryId: Int, newName: String) {
        viewModelScope.launch {
            recipeCategoryRepository.renameCategory(categoryId, newName)
        }
    }
}