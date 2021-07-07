package ru.cookedapp.cooked.data.db

import android.content.Context
import androidx.core.content.edit
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.dao.*
import ru.cookedapp.cooked.data.db.entity.*
import ru.cookedapp.cooked.data.prefs.RecipePreferences
import java.util.concurrent.Executors

@Database(
    entities = [
        Recipe::class,
        Product::class,
        RecipeCategory::class,
        ProductUnit::class,
        CartItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productUnitsDao(): ProductUnitsDao

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeCategoryDao(): RecipeCategoryDao

    abstract fun cartDao(): CartDao

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
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            val database = getInstance(context)
                            val res = context.resources

                            // Prepopulate units
                            with(database.productUnitsDao()) {
                                val units = res.getStringArray(R.array.labels_units)
                                units.forEach {
                                    insert(ProductUnit(name = it))
                                }
                            }

                            // Create category 'No category'
                            val noCategoryId = database.recipeCategoryDao()
                                .insert(RecipeCategory(name = res.getString(R.string.label_no_category)))
                                .toInt()

                            context.getSharedPreferences(
                                RecipePreferences.PREFERENCES_NAME,
                                Context.MODE_PRIVATE
                            ).edit {
                                putInt(RecipePreferences.FIELD_NO_CATEGORY, noCategoryId)
                            }
                        }
                    }
                })
                .build()
        }
    }
}
