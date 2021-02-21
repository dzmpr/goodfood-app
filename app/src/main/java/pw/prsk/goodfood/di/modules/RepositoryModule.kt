package pw.prsk.goodfood.di.modules

import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.repository.*

@Module
class RepositoryModule {
    @Provides
    fun provideMealRepository(dbInstance: AppDatabase): MealRepository =
        MealRepository(dbInstance)

    @Provides
    fun provideProductRepository(dbInstance: AppDatabase): ProductRepository =
        ProductRepository(dbInstance)

    @Provides
    fun provideMealCategoryRepository(dbInstance: AppDatabase): MealCategoryRepository =
        MealCategoryRepository(dbInstance)

    @Provides
    fun provideProductUnitsRepository(dbInstance: AppDatabase): ProductUnitsRepository =
        ProductUnitsRepository(dbInstance)
}