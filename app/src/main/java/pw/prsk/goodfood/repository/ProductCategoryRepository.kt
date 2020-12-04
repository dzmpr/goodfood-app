package pw.prsk.goodfood.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.ProductCategory

class ProductCategoryRepository(private val dbInstance: AppDatabase) {
    suspend fun getCategories(): List<ProductCategory> = withContext(Dispatchers.IO) {
        dbInstance.productCategoryDao().getAll()
    }
}