package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.dao.RecipeCategoryDao
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity

class RecipeCategoryRepository(private val recipeCategoryDao: RecipeCategoryDao) {
    suspend fun getCategories(): List<RecipeCategoryEntity> = withContext(Dispatchers.IO) {
        recipeCategoryDao.getAll()
    }

    suspend fun getCategoriesFlow() = withContext(Dispatchers.IO) {
        recipeCategoryDao.getCategoriesFlow()
    }

    suspend fun renameCategory(id: Int, categoryName: String) = withContext(Dispatchers.IO) {
        recipeCategoryDao.renameCategory(id, categoryName)
    }
}
