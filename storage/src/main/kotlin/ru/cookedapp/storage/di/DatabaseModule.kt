package ru.cookedapp.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.cookedapp.storage.core.DaoProvider

@Module
internal interface DatabaseModule {

    companion object {

        @Provides
        fun provideCartDao(context: Context, provider: DaoProvider) = provider.getCartDao(context)

        @Provides
        fun provideRecipeDao(context: Context, provider: DaoProvider) = provider.getRecipeDao(context)

        @Provides
        fun provideProductDao(context: Context, provider: DaoProvider) = provider.getProductDao(context)

        @Provides
        fun provideRecipeCategoryDao(context: Context, provider: DaoProvider) = provider.getRecipeCategoryDao(context)

        @Provides
        fun provideProductUnitDao(context: Context, provider: DaoProvider) = provider.getProductUnitDao(context)
    }
}
