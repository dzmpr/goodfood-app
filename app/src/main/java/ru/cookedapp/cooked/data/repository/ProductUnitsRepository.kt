package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.AppDatabase

class ProductUnitsRepository(private val dbInstance: AppDatabase) {
    suspend fun getUnits() = withContext(Dispatchers.IO) {
        dbInstance.productUnitsDao().getAll()
    }
}
