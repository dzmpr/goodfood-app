package ru.cookedapp.cooked.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cookedapp.cooked.di.ViewModelFactory
import ru.cookedapp.cooked.ui.cart.CartViewModel
import ru.cookedapp.cooked.ui.editRecipe.EditRecipeViewModel
import ru.cookedapp.cooked.ui.manageItems.manageCategories.ManageCategoriesViewModel
import ru.cookedapp.cooked.ui.manageItems.manageProducts.ManageProductsViewModel
import ru.cookedapp.cooked.ui.recipeDetails.RecipeDetailsViewModel
import ru.cookedapp.cooked.ui.recipeList.RecipeListViewModel
import ru.cookedapp.cooked.ui.recipesOverview.RecipesOverviewViewModel
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
    @ViewModelKey(RecipeDetailsViewModel::class)
    abstract fun provideRecipeViewModel(recipeDetailsViewModel: RecipeDetailsViewModel): ViewModel

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
