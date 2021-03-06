package pw.prsk.goodfood.di.components

import android.app.Application
import dagger.Component
import pw.prsk.goodfood.di.modules.ApplicationModule
import pw.prsk.goodfood.di.modules.GatewayModule
import pw.prsk.goodfood.di.modules.RepositoryModule
import pw.prsk.goodfood.di.modules.RoomModule
import pw.prsk.goodfood.viewmodels.EditRecipeViewModel
import pw.prsk.goodfood.viewmodels.RecipesOverviewViewModel
import pw.prsk.goodfood.viewmodels.ProductsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, RoomModule::class, GatewayModule::class])
interface AppComponent {
    fun context(): Application

    fun inject(viewModel: RecipesOverviewViewModel)
    fun inject(viewModel: ProductsViewModel)
    fun inject(viewModel: EditRecipeViewModel)
}