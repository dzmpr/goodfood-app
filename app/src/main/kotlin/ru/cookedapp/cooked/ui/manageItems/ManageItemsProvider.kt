package ru.cookedapp.cooked.ui.manageItems

import javax.inject.Inject
import ru.cookedapp.common.baseList.data.Items
import ru.cookedapp.storage.entity.ProductEntity
import ru.cookedapp.storage.entity.RecipeCategoryEntity

class ManageItemsProvider @Inject constructor(
    private val itemsFactory: ManageItemsFactory,
) {

    fun generateRowsFromProducts(
        products: List<ProductEntity>
    ): List<Items> = products.map { product ->
        itemsFactory.createManageItem(product.id, product.name)
    }

    fun generateRowsFromCategories(
        categories: List<RecipeCategoryEntity>
    ): List<Items> = categories.map { category ->
        itemsFactory.createManageItem(category.id, category.name)
    }
}
