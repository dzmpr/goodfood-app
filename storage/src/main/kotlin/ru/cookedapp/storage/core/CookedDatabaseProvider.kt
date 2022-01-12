package ru.cookedapp.storage.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton
import ru.cookedapp.common.resourceProvider.ResourceProvider
import ru.cookedapp.storage.R
import ru.cookedapp.storage.entity.ProductUnitEntity
import ru.cookedapp.storage.entity.RecipeCategoryEntity
import ru.cookedapp.storage.recipePreferences.RecipePreferences

@Singleton
internal class CookedDatabaseProvider @Inject constructor(
    private val recipePreferences: RecipePreferences,
    private val rp: ResourceProvider,
) {
    private var instance: CookedDatabase? = null

    fun getDatabase(context: Context): CookedDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

    private fun buildDatabase(context: Context): CookedDatabase {
        return Room.databaseBuilder(context, CookedDatabase::class.java, Config.DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        val database = getDatabase(context)
                        val res = context.resources

                        // Prepopulate units
                        with(database.productUnitDao()) {
                            val units = res.getStringArray(R.array.labels_units)
                            units.forEach {
                                insert(ProductUnitEntity(id = 0, name = it))
                            }
                        }

                        // Create category 'No category'
                        val noCategoryId = database.recipeCategoryDao().insert(
                            RecipeCategoryEntity(id = 0, name = rp.getString(R.string.label_no_category))
                        )
                        recipePreferences.recipeNoCategoryId = noCategoryId
                    }
                }
            }
        ).build()
    }
}
