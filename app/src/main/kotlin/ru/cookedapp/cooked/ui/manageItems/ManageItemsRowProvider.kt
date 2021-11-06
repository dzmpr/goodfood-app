package ru.cookedapp.cooked.ui.manageItems

import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity

class ManageItemsRowProvider {

    private val rowFactory = ManageItemsRowFactory()

    fun generateRowsFromProducts(
        products: List<ProductEntity>
    ) = products.map { product ->
        rowFactory.createManageItem(product.id!!, product.name)
    }

    fun generateRowsFromCategories(
        categories: List<RecipeCategoryEntity>
    ) = categories.map { category ->
        rowFactory.createManageItem(category.id!!, category.name)
    }
}
