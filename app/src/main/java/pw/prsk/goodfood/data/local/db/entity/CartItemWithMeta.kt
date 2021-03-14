package pw.prsk.goodfood.data.local.db.entity

data class CartItemWithMeta(
    val id: Int? = null,
    var isBought: Boolean = false,
    val productId: Product,
    var amount: Float,
    val unitId: ProductUnit
)
