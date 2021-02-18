package pw.prsk.goodfood.data

data class IngredientWithMeta(
    var product_id: Int,
    var product_name: String,
    var amount: Float,
    var unit_id: Int,
    var unit_name: String
)
