package pw.prsk.goodfood.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pw.prsk.goodfood.di.ViewModelFactory
import pw.prsk.goodfood.utils.ViewModelKey
import pw.prsk.goodfood.presentation.viewmodel.RecipeListViewModel
import pw.prsk.goodfood.presentation.viewmodel.RecipeViewModel
import pw.prsk.goodfood.presentation.viewmodel.RecipesOverviewViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipesOverviewViewModel::class)
    abstract fun provideRecipesOverviewViewModel(recipesOverviewViewModel: RecipesOverviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun provideRecipeListViewModel(recipeListViewModel: RecipeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun provideRecipeViewModel(recipeViewModel: RecipeViewModel): ViewModel
}