package ru.cookedapp.cooked.ui.manageCategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository

class ManageCategoriesViewModel @Inject constructor(
    private val recipeCategoryRepository: RecipeCategoryRepository
) : ViewModel() {
    private val categories by lazy {
        MutableLiveData<List<RecipeCategoryEntity>>()
    }
    val categoryList: LiveData<List<RecipeCategoryEntity>>
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
