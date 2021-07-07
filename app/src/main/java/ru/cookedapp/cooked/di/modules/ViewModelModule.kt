package ru.cookedapp.cooked.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cookedapp.cooked.di.ViewModelFactory
import ru.cookedapp.cooked.presentation.viewmodel.*
import ru.cookedapp.cooked.utils.ViewModelKey


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

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun provideCartViewModel(cartViewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManageCategoriesViewModel::class)
    abstract fun provideManageCategoriesViewModel(manageCategoriesViewModel: ManageCategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManageProductsViewModel::class)
    abstract fun provideManageProductsViewModel(manageProductsViewModel: ManageProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditRecipeViewModel::class)
    abstract fun provideEditRecipeViewModel(editRecipeViewModel: EditRecipeViewModel): ViewModel
}
