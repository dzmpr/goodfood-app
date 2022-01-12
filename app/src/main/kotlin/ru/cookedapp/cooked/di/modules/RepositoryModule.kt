package ru.cookedapp.cooked.di.modules

import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.data.repository.CartRepository
import ru.cookedapp.cooked.data.repository.ProductRepository
import ru.cookedapp.cooked.data.repository.ProductUnitsRepository
import ru.cookedapp.cooked.data.repository.RecipeCategoryRepository
import ru.cookedapp.cooked.data.repository.RecipeRepository
import ru.cookedapp.storage.dao.CartDao
import ru.cookedapp.storage.dao.ProductDao
import ru.cookedapp.storage.dao.ProductUnitDao
import ru.cookedapp.storage.dao.RecipeCategoryDao
import ru.cookedapp.storage.dao.RecipeDao

@Module
class RepositoryModule {
    @Provides
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        recipeCategoryDao: RecipeCategoryDao,
        productDao: ProductDao,
        productUnitDao: ProductUnitDao,
        photoGateway: PhotoGateway,
    ): RecipeRepository = RecipeRepository(
        recipeDao,
        recipeCategoryDao,
        productDao,
        productUnitDao,
        photoGateway,
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
