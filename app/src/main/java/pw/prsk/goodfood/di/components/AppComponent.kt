package pw.prsk.goodfood.di.components

import android.app.Application
import dagger.Component
import pw.prsk.goodfood.MealsFragment
import pw.prsk.goodfood.ProductsFragment
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.di.modules.ApplicationModule
import pw.prsk.goodfood.di.modules.RepositoryModule
import pw.prsk.goodfood.di.modules.RoomModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, RoomModule::class])
interface AppComponent {
    fun context(): Application
    fun inject(fragment: MealsFragment)
    fun inject(fragment: ProductsFragment)
}