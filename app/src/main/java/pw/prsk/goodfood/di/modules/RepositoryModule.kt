package pw.prsk.goodfood.di.modules

import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.repository.MealCategoryRepository
import pw.prsk.goodfood.repository.MealRepository
import pw.prsk.goodfood.repository.ProductCategoryRepository
import pw.prsk.goodfood.repository.ProductRepository

@Module
class RepositoryModule {
    @Provides
    fun provideMealRepository(dbInstance: AppDatabase): MealRepository =
        MealRepository(dbInstance)

    @Provides
    fun provideProductRepository(dbInstance: AppDatabase): ProductRepository =
        ProductRepository(dbInstance)

    @Provides
    fun provideProductCategoryRepository(dbInstance: AppDatabase): ProductCategoryRepository =
        ProductCategoryRepository(dbInstance)

    @Provides
    fun provideMealCategoryRepository(dbInstance: AppDatabase): MealCategoryRepository =
        MealCategoryRepository(dbInstance)
}