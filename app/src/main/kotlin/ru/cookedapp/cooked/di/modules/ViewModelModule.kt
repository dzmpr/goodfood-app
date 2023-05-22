package ru.cookedapp.cooked.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.cookedapp.cooked.di.ViewModelFactory
import ru.cookedapp.cooked.ui.cart.CartViewModel
import ru.cookedapp.cooked.ui.categories.CategoriesViewModel
import ru.cookedapp.cooked.ui.editRecipe.EditRecipeViewModel
import ru.cookedapp.cooked.ui.recipeDetails.RecipeDetailsViewModel
import ru.cookedapp.cooked.ui.recipeList.RecipeListViewModel
import ru.cookedapp.cooked.ui.recipesOverview.RecipesOverviewViewModel
import ru.cookedapp.cooked.ui.settings.SettingsViewModel
import ru.cookedapp.cooked.utils.ViewModelKey


@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipesOverviewViewModel::class)
    abstract fun provideRecipesOverviewViewModel(impl: RecipesOverviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun provideRecipeListViewModel(impl: RecipeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailsViewModel::class)
    abstract fun provideRecipeViewModel(impl: RecipeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun provideCartViewModel(impl: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun provideManageCategoriesViewModel(impl: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditRecipeViewModel::class)
    abstract fun provideEditRecipeViewModel(impl: EditRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun provideSettingsViewModel(impl: SettingsViewModel): ViewModel
}
