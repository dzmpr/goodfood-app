package ru.cookedapp.cooked.ui.manageItems.manageCategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository
import ru.cookedapp.cooked.ui.manageItems.ManageItemsProvider
import ru.cookedapp.cooked.utils.listBase.data.Items

class ManageCategoriesViewModel @Inject constructor(
    private val recipeCategoryRepository: RecipeCategoryRepository,
    private val itemsProvider: ManageItemsProvider,
) : ViewModel() {

    private val _categoryList by lazy {
        MutableLiveData<List<Items>>()
    }
    val categoryList: LiveData<List<Items>>
        get() = _categoryList

    init {
        viewModelScope.launch {
            recipeCategoryRepository.getCategoriesFlow().onEach { categories ->
                _categoryList.value = itemsProvider.generateRowsFromCategories(categories)
            }.launchIn(this)
        }
    }

    fun onCategoryRenamed(categoryId: Long, newName: String) {
        viewModelScope.launch {
            recipeCategoryRepository.renameCategory(categoryId, newName)
        }
    }
}
