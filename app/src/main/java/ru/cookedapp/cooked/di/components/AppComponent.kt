package ru.cookedapp.cooked.di.components

import android.app.Application
import dagger.Component
import ru.cookedapp.cooked.presentation.adapter.RecipeCardAdapter
import ru.cookedapp.cooked.presentation.adapter.RecipeLineAdapter
import ru.cookedapp.cooked.di.modules.*
import ru.cookedapp.cooked.presentation.ui.*
import ru.cookedapp.cooked.presentation.viewmodel.EditRecipeViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    RepositoryModule::class,
    RoomModule::class,
    GatewayModule::class,
    ViewModelModule::class,
    PreferenceModule::class
]
)
interface AppComponent {
    fun context(): Application
    fun inject(app: CookedApp)

    fun inject(fragment: RecipesOverviewFragment)
    fun inject(fragment: RecipeListFragment)
    fun inject(fragment: RecipeFragment)
    fun inject(fragment: CartFragment)
    fun inject(fragment: ManageCategoriesFragment)
    fun inject(fragment: ManageProductsFragment)
    fun inject(fragment: EditRecipeFragment)
    fun inject(fragment: AddIngredientBottomFragment)

    fun inject(adapter: RecipeCardAdapter)
    fun inject(adapter: RecipeLineAdapter)

    fun inject(viewModel: EditRecipeViewModel)
}
