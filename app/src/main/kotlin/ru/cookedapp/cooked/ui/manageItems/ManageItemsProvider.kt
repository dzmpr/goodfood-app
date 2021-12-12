package ru.cookedapp.cooked.ui.manageItems

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.utils.listBase.data.Items

class ManageItemsProvider @Inject constructor(
    private val itemsFactory: ManageItemsFactory,
) {

    fun generateRowsFromProducts(
        products: List<ProductEntity>
    ): List<Items> = products.map { product ->
        itemsFactory.createManageItem(product.id!!, product.name)
    }

    fun generateRowsFromCategories(
        categories: List<RecipeCategoryEntity>
    ): List<Items> = categories.map { category ->
        itemsFactory.createManageItem(category.id!!, category.name)
    }
}