package pw.prsk.goodfood.data

data class IngredientWithMeta(
    val product: Product,
    var amount: Float,
    val unit: ProductUnit
)
