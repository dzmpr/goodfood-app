package ru.cookedapp.cooked.di.components

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import ru.cookedapp.cooked.di.modules.GatewayModule
import ru.cookedapp.cooked.di.modules.RepositoryModule
import ru.cookedapp.cooked.di.modules.UtilitiesModule
import ru.cookedapp.cooked.di.modules.ViewModelModule
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.cart.CartFragment
import ru.cookedapp.cooked.ui.editRecipe.AddIngredientBottomFragment
import ru.cookedapp.cooked.ui.editRecipe.EditRecipeFragment
import ru.cookedapp.cooked.ui.manageItems.BaseManageItemsFragment
import ru.cookedapp.cooked.ui.recipeDetails.RecipeDetailsFragment
import ru.cookedapp.cooked.ui.recipeList.RecipeListFragment
import ru.cookedapp.cooked.ui.recipesOverview.RecipeCardAdapter
import ru.cookedapp.cooked.ui.recipesOverview.RecipesOverviewFragment
import ru.cookedapp.storage.di.StorageModule

@Singleton
@Component(
    modules = [
        StorageModule::class,
        RepositoryModule::class,
        GatewayModule::class,
        ViewModelModule::class,
        UtilitiesModule::class,
    ]
)
interface AppComponent {

    fun inject(app: CookedApp)

    // Fragments
    fun inject(fragment: RecipesOverviewFragment)
    fun inject(fragment: RecipeListFragment)
    fun inject(fragment: RecipeDetailsFragment)
    fun inject(fragment: CartFragment)
    fun inject(fragment: BaseManageItemsFragment)
    fun inject(fragment: EditRecipeFragment)
    fun inject(fragment: AddIngredientBottomFragment)

    // Adapters
    fun inject(adapter: RecipeCardAdapter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun bindContext(context: Context): Builder

        @BindsInstance
        fun bindApplication(app: Application): Builder
    }
}
