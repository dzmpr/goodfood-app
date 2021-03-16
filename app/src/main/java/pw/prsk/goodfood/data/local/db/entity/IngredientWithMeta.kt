package pw.prsk.goodfood.data.local.db.entity

data class IngredientWithMeta(
    val product: Product,
    var amount: Float,
    val unit: ProductUnit
)
