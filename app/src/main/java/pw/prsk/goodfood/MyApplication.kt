package pw.prsk.goodfood

import android.app.Application
import pw.prsk.goodfood.di.components.AppComponent
import pw.prsk.goodfood.di.components.DaggerAppComponent
import pw.prsk.goodfood.di.modules.ApplicationModule

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}