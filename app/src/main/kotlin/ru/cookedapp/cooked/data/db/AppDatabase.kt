package ru.cookedapp.cooked.data.db

import android.content.Context
import androidx.core.content.edit
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.dao.CartDao
import ru.cookedapp.cooked.data.db.dao.ProductDao
import ru.cookedapp.cooked.data.db.dao.ProductUnitDao
import ru.cookedapp.cooked.data.db.dao.RecipeCategoryDao
import ru.cookedapp.cooked.data.db.dao.RecipeDao
import ru.cookedapp.cooked.data.db.entity.CartItemEntity
import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.data.db.entity.ProductUnitEntity
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.data.db.entity.RecipeEntity
import ru.cookedapp.cooked.data.prefs.RecipePreferences

@Database(
    entities = [
        RecipeEntity::class,
        ProductEntity::class,
        RecipeCategoryEntity::class,
        ProductUnitEntity::class,
        CartItemEntity::class
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productUnitDao(): ProductUnitDao

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
                            with(database.productUnitDao()) {
                                val units = res.getStringArray(R.array.labels_units)
                                units.forEach {
                                    insert(ProductUnitEntity(id = 0, name = it))
                                }
                            }

                            // Create category 'No category'
                            val noCategoryId = database.recipeCategoryDao()
                                .insert(RecipeCategoryEntity(id = 0, name = res.getString(R.string.label_no_category)))

                            context.getSharedPreferences(
                                RecipePreferences.PREFERENCES_NAME,
                                Context.MODE_PRIVATE
                            ).edit {
                                putLong(RecipePreferences.FIELD_NO_CATEGORY, noCategoryId)
                            }
                        }
                    }
                })
                .build()
        }
    }
}
