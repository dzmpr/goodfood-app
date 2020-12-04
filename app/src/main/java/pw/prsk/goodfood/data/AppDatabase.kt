package pw.prsk.goodfood.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pw.prsk.goodfood.R
import java.util.concurrent.Executors

@Database(
    entities = [Meal::class, Product::class, MealCategory::class, ProductCategory::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productCategoryDao(): ProductCategoryDao

    abstract fun mealDao(): MealDao
    abstract fun mealCategoryDao(): MealCategoryDao

    companion object {
        private const val DATABASE_NAME = "food-db"

        private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `meal_categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL)")
                database.execSQL("CREATE TABLE IF NOT EXISTS `product_categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL)")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            val database = getInstance(context)
                            val res = context.resources
                            database.mealCategoryDao().insert(MealCategory(name = res.getString(R.string.default_meal_category_1)))
                            database.mealCategoryDao().insert(MealCategory(name = res.getString(R.string.default_meal_category_2)))
                            database.mealCategoryDao().insert(MealCategory(name = res.getString(R.string.default_meal_category_3)))

                            database.productCategoryDao().insert(ProductCategory(name = res.getString(R.string.default_product_category_1)))
                            database.productCategoryDao().insert(ProductCategory(name = res.getString(R.string.default_product_category_2)))
                            database.productCategoryDao().insert(ProductCategory(name = res.getString(R.string.default_product_category_3)))
                        }
                    }
                })
                .build()
        }
    }
}