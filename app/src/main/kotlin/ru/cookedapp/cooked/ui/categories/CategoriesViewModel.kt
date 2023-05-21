package ru.cookedapp.cooked.ui.categories

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository
import ru.cookedapp.cooked.ui.base.ComposeViewModel
import ru.cookedapp.cooked.ui.categories.data.CategoriesState
import ru.cookedapp.cooked.ui.categories.data.CategoryData
import ru.cookedapp.storage.entity.RecipeCategoryEntity
import javax.inject.Inject

internal class CategoriesViewModel @Inject constructor(
    private val recipeCategoryRepository: RecipeCategoryRepository,
): ComposeViewModel<CategoriesState>() {

    override val initialState = CategoriesState(categories = emptyList())

    init {
        viewModelScope.launch {
            recipeCategoryRepository.getCategoriesFlow().onEach { categoriesList ->
                val categoryData = categoriesList.toCategoryData()
                updateState {
                    copy(categories = categoryData)
                }
            }.launchIn(this)
        }
    }

    private fun List<RecipeCategoryEntity>.toCategoryData(): List<CategoryData> = map { category ->
        CategoryData(category.id, category.name)
    }

    fun onCategoryRenamed(categoryId: Long, newName: String) {
        viewModelScope.launch {
            recipeCategoryRepository.renameCategory(categoryId, newName)
        }
    }
}
