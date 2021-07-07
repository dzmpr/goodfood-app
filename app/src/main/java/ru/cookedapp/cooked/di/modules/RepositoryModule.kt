package ru.cookedapp.cooked.di.modules

import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.db.AppDatabase
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.prefs.RecipePreferences
import ru.cookedapp.cooked.data.repository.CartRepository
import ru.cookedapp.cooked.data.repository.ProductRepository
import ru.cookedapp.cooked.data.repository.ProductUnitsRepository
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository
import ru.cookedapp.cooked.data.repository.RecipeRepository

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
