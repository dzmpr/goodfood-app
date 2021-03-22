package pw.prsk.goodfood.di.components

import android.app.Application
import dagger.Component
import pw.prsk.goodfood.presentation.adapter.RecipeCardAdapter
import pw.prsk.goodfood.presentation.adapter.RecipeLineAdapter
import pw.prsk.goodfood.di.modules.*
import pw.prsk.goodfood.presentation.ui.*
import pw.prsk.goodfood.presentation.viewmodel.EditRecipeViewModel
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
    fun inject(app: MyApplication)

    fun inject(fragment: RecipesOverviewFragment)
    fun inject(fragment: RecipeListFragment)
    fun inject(fragment: RecipeFragment)
    fun inject(fragment: CartFragment)
    fun inject(fragment: ManageCategoriesFragment)

    fun inject(adapter: RecipeCardAdapter)
    fun inject(adapter: RecipeLineAdapter)

    fun inject(viewModel: EditRecipeViewModel)
}