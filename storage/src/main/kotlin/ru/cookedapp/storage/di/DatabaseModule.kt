package ru.cookedapp.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.cookedapp.storage.core.CookedDatabaseProvider
import ru.cookedapp.storage.dao.ProductDao
import ru.cookedapp.storage.dao.ProductUnitDao
import ru.cookedapp.storage.dao.RecipeCategoryDao
import ru.cookedapp.storage.dao.RecipeDao

@Module
internal interface DatabaseModule {

    companion object {

        @Provides
        fun provideCartDao(context: Context) = CookedDatabaseProvider.getInstance(context).cartDao()

        @Provides
        fun provideRecipeDao(context: Context): RecipeDao {
            return CookedDatabaseProvider.getInstance(context).recipeDao()
        }

        @Provides
        fun provideProductDao(context: Context): ProductDao {
            return CookedDatabaseProvider.getInstance(context).productDao()
        }

        @Provides
        fun provideRecipeCategoryDao(context: Context): RecipeCategoryDao {
            return CookedDatabaseProvider.getInstance(context).recipeCategoryDao()
        }

        @Provides
        fun provideProductUnitDao(context: Context): ProductUnitDao {
            return CookedDatabaseProvider.getInstance(context).productUnitDao()
        }
    }
}
