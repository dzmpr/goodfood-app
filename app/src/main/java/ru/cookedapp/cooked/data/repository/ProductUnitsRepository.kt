package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.dao.ProductUnitDao

class ProductUnitsRepository(private val productUnitDao: ProductUnitDao) {
    suspend fun getUnits() = withContext(Dispatchers.IO) {
        productUnitDao.getAll()
    }
}
