package ru.cookedapp.cooked.di.modules

import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.local.db.AppDatabase
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.local.prefs.RecipePreferences
import ru.cookedapp.cooked.data.repository.*

@Module
class RepositoryModule {
    @Provides
    fun provideRecipeRepository(
        dbInstance: AppDatabase,
        photoGateway: PhotoGateway,
        recipePreferences: RecipePreferences
    ): RecipeRepository =
        RecipeRepository(dbInstance, photoGateway, recipePreferences)

    @Provides
    fun provideProductRepository(dbInstance: AppDatabase): ProductRepository =
        ProductRepository(dbInstance)

    @Provides
    fun provideRecipeCategoryRepository(dbInstance: AppDatabase): RecipeCategoryRepository =
        RecipeCategoryRepository(dbInstance)

    @Provides
    fun provideProductUnitsRepository(dbInstance: AppDatabase): ProductUnitsRepository =
        ProductUnitsRepository(dbInstance)

    @Provides
    fun provideCartRepository(dbInstance: AppDatabase): CartRepository =
        CartRepository(dbInstance)
}
