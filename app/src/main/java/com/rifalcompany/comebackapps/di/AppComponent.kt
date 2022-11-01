package com.rifalcompany.comebackapps.di

import com.rifalcompany.comebackapps.features.home.di.HomeModule
import com.rifalcompany.comebackapps.features.home.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HomeModule::class])

interface AppComponent {
    fun injectRepository(homeViewModel: HomeViewModel)
}