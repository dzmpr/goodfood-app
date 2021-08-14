package ru.cookedapp.cooked.di.modules

import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.db.dao.CartDao
import ru.cookedapp.cooked.data.db.dao.ProductDao
import ru.cookedapp.cooked.data.db.dao.ProductUnitDao
import ru.cookedapp.cooked.data.db.dao.RecipeCategoryDao
import ru.cookedapp.cooked.data.db.dao.RecipeDao
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
        recipeDao: RecipeDao,
        recipeCategoryDao: RecipeCategoryDao,
        productDao: ProductDao,
        productUnitDao: ProductUnitDao,
        photoGateway: PhotoGateway,
        recipePreferences: RecipePreferences
    ): RecipeRepository = RecipeRepository(
        recipeDao,
        recipeCategoryDao,
        productDao,
        productUnitDao,
        photoGateway,
        recipePreferences
    )

    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository =
        ProductRepository(productDao)

    @Provides
    fun provideRecipeCategoryRepository(recipeCategoryDao: RecipeCategoryDao): RecipeCategoryRepository =
        RecipeCategoryRepository(recipeCategoryDao)

    @Provides
    fun provideProductUnitsRepository(productUnitDao: ProductUnitDao): ProductUnitsRepository =
        ProductUnitsRepository(productUnitDao)

    @Provides
    fun provideCartRepository(
        recipeDao: RecipeDao,
        cartDao: CartDao,
        productDao: ProductDao,
        productUnitDao: ProductUnitDao
    ): CartRepository = CartRepository(
        recipeDao,
        cartDao,
        productDao,
        productUnitDao
    )
}
