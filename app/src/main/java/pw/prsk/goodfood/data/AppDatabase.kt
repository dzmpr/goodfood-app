package pw.prsk.goodfood.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.dao.*
import java.util.concurrent.Executors

@Database(
    entities = [
        Recipe::class,
        Product::class,
        RecipeCategory::class,
        ProductUnit::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeCategoryDao(): RecipeCategoryDao

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