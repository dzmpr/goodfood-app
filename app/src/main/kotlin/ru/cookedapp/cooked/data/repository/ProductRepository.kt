package ru.cookedapp.cooked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cookedapp.storage.dao.ProductDao
import ru.cookedapp.storage.entity.ProductEntity

class ProductRepository(private val productDao: ProductDao) {
    suspend fun addProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        productDao.insert(product)
    }

    suspend fun getProducts(): List<ProductEntity> = withContext(Dispatchers.IO) {
        productDao.getAll()
    }

    suspend fun getProductsFlow() = withContext(Dispatchers.IO) {
        productDao.getProductsFlow()
    }

    suspend fun removeProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        productDao.delete(product)
    }

    suspend fun removeById(id: Long) = withContext(Dispatchers.IO) {
        productDao.deleteById(id)
    }

    suspend fun increaseUsage(id: Long) = withContext(Dispatchers.IO) {
        productDao.increaseUsages(id)
    }

    suspend fun decreaseUsage(id: Long) = withContext(Dispatchers.IO) {
        productDao.decreaseUsages(id)
    }

    suspend fun renameProduct(id: Long, name: String) = withContext(Dispatchers.IO) {
        productDao.renameProduct(id, name)
    }
}
