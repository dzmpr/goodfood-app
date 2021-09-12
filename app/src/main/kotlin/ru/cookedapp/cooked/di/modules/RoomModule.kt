package ru.cookedapp.cooked.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.cookedapp.cooked.data.db.AppDatabase

@Module
interface RoomModule {
    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(context: Application): AppDatabase =
            AppDatabase.getInstance(context)

        @Provides
        @Singleton
        fun provideCartDao(db: AppDatabase) = db.cartDao()

        @Provides
        @Singleton
        fun provideRecipeDao(db: AppDatabase) = db.recipeDao()

        @Provides
        @Singleton
        fun provideProductDao(db: AppDatabase) = db.productDao()

        @Provides
        @Singleton
        fun provideRecipeCategoryDao(db: AppDatabase) = db.recipeCategoryDao()

        @Provides
        @Singleton
        fun provideProductUnitDao(db: AppDatabase) = db.productUnitDao()
    }
}
