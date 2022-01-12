package ru.cookedapp.storage.di

import dagger.Module

@Module(includes = [
    DatabaseModule::class,
    PreferencesModule::class,
])
interface StorageModule
