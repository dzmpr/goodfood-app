package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.data.db.AppDatabase
import ru.cookedapp.cooked.data.db.entity.Product

class ProductRepository(private val dbInstance: AppDatabase) {
    suspend fun addProduct(product: Product) = withContext(Dispatchers.IO) {
        dbInstance.productDao().insert(product)
    }

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        dbInstance.productDao().getAll()
    }

    suspend fun getProductsFlow() = withContext(Dispatchers.IO) {
        dbInstance.productDao().getProductsFlow()
    }

    suspend fun removeProduct(product: Product) = withContext(Dispatchers.IO) {
        dbInstance.productDao().delete(product)
    }

    suspend fun removeById(id: Int) = withContext(Dispatchers.IO) {
        dbInstance.productDao().deleteById(id)
    }

    suspend fun increaseUsage(id: Int) = withContext(Dispatchers.IO) {
        dbInstance.productDao().increaseUsages(id)
    }

    suspend fun decreaseUsage(id: Int) = withContext(Dispatchers.IO) {
        dbInstance.productDao().decreaseUsages(id)
    }

    suspend fun renameProduct(id: Int, name: String) = withContext(Dispatchers.IO) {
        dbInstance.productDao().renameProduct(id, name)
    }
}
