package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.storage.dao.ProductUnitDao

class ProductUnitsRepository(private val productUnitDao: ProductUnitDao) {
    suspend fun getUnits() = withContext(Dispatchers.IO) {
        productUnitDao.getAll()
    }
}
