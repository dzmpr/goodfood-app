package pw.prsk.goodfood.data

import androidx.room.*

@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): Product

    @Query("DELETE FROM products WHERE id = :id")
    fun deleteById(id: Int)

    @Query("""
        SELECT 
            products.id, 
            products.name, 
            cats.name as category_name, 
            units.name as unit_name
        FROM products, product_categories as cats, product_units as units
        WHERE cats.id == products.categoryId 
        AND units.id == products.unitId""")
    fun getProductsWithMeta(): List<ProductWithMeta>
}