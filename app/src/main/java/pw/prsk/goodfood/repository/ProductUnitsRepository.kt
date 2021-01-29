package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase

class ProductUnitsRepository(private val dbInstance: AppDatabase) {
    suspend fun getUnits() = withContext(Dispatchers.IO) {
        dbInstance.productUnitsDao().getAll()
    }
}