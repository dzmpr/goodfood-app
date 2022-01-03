package ru.cookedapp.cooked.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.cookedapp.common.resourceProvider.ResourceProvider
import ru.cookedapp.common.resourceProvider.ResourceProviderImpl

@Module
interface UtilitiesModule {

    companion object {

        @Provides
        fun provideResourceProvider(
            context: Context,
        ): ResourceProvider = ResourceProviderImpl(context)
    }
}
