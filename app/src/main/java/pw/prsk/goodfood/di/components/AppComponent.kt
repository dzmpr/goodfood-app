package pw.prsk.goodfood.di.components

import android.app.Application
import dagger.Component
import pw.prsk.goodfood.RecipesOverviewFragment
import pw.prsk.goodfood.adapters.RecipeCardAdapter
import pw.prsk.goodfood.di.modules.*
import pw.prsk.goodfood.viewmodels.EditRecipeViewModel
import pw.prsk.goodfood.viewmodels.ProductsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    RepositoryModule::class,
    RoomModule::class,
    GatewayModule::class,
    ViewModelModule::class
]
)
interface AppComponent {
    fun context(): Application

    fun inject(fragment: RecipesOverviewFragment)

    fun inject(adapter: RecipeCardAdapter)

    fun inject(viewModel: ProductsViewModel)
    fun inject(viewModel: EditRecipeViewModel)
}