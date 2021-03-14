package pw.prsk.goodfood.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.local.db.AppDatabase

class ProductUnitsRepository(private val dbInstance: AppDatabase) {
    suspend fun getUnits() = withContext(Dispatchers.IO) {
        dbInstance.productUnitsDao().getAll()
    }
}