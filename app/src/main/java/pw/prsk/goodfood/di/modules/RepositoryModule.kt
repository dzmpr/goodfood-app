package pw.prsk.goodfood.di.modules

import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.local.db.AppDatabase
import pw.prsk.goodfood.data.gateway.PhotoGateway
import pw.prsk.goodfood.data.local.prefs.RecipePreferences
import pw.prsk.goodfood.data.repository.*

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