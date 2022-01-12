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

@Singleton
internal class CookedDatabaseProvider @Inject constructor(
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

                        // Prepopulate units
                        with(database.productUnitDao()) {
                            val units = rp.getStringArray(R.array.labels_units)
                            units.forEach { unitName ->
                                insert(ProductUnitEntity(name = unitName))
                            }
                        }
                    }
                }
            }
        ).build()
    }
}
