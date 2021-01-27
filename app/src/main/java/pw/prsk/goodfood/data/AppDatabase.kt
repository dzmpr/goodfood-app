package pw.prsk.goodfood.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pw.prsk.goodfood.R
import java.util.concurrent.Executors

@Database(
    entities = [
        Meal::class,
        Product::class,
        MealCategory::class,
        ProductCategory::class,
        ProductUnit::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productCategoryDao(): ProductCategoryDao

    abstract fun mealDao(): MealDao
    abstract fun mealCategoryDao(): MealCategoryDao

    abstract fun productUnitsDao(): ProductUnitsDao

    companion object {
        private const val DATABASE_NAME = "food-db"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration() //FIXME: Remove on release
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            val database = getInstance(context)
                            val res = context.resources

                            // Prepopulate meal categories
                            with(database.mealCategoryDao()) {
                                insert(MealCategory(name = res.getString(R.string.default_meal_category_1)))
                                insert(MealCategory(name = res.getString(R.string.default_meal_category_2)))
                                insert(MealCategory(name = res.getString(R.string.default_meal_category_3)))
                            }

                            // Prepopulate product categories
                            with(database.productCategoryDao()) {
                                insert(ProductCategory(name = res.getString(R.string.default_product_category_1)))
                                insert(ProductCategory(name = res.getString(R.string.default_product_category_2)))
                                insert(ProductCategory(name = res.getString(R.string.default_product_category_3)))
                            }

                            // Prepopulate units
                            with(database.productUnitsDao()) {
                                insert(ProductUnit(name = res.getString(R.string.default_product_unit_1)))
                                insert(ProductUnit(name = res.getString(R.string.default_product_unit_2)))
                                insert(ProductUnit(name = res.getString(R.string.default_product_unit_3)))
                            }
                        }
                    }
                })
                .build()
        }
    }
}