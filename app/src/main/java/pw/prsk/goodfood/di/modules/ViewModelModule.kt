package pw.prsk.goodfood.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pw.prsk.goodfood.di.ViewModelFactory
import pw.prsk.goodfood.utils.ViewModelKey
import pw.prsk.goodfood.viewmodels.RecipesOverviewViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipesOverviewViewModel::class)
    abstract fun provideRecipesOverviewViewModel(recipesOverviewViewModel: RecipesOverviewViewModel): ViewModel
}