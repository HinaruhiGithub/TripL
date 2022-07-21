package com.example.tripjump.DIModule

import com.example.tripjump.Category.InputAccepter
import com.example.tripjump.Category.SelectPresenter
import com.example.tripjump.Category.Usecase
import com.example.tripjump.Destination.BaseMap.Presenter
import com.example.tripjump.Destination.BaseMap.View
import com.example.tripjump.Destination.PlaceCollection.Updater
import com.example.tripjump.Destination.Search.Provider
import com.example.tripjump.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
//@Component(modules = [UniModule::class, MainActivityModule::class])
@Component(modules = [UniModule::class, UpdaterModule::class, ObserveModule::class, CollectorModule::class, ListViewModule::class, TransitionModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(view: View)
    fun inject(presenter: Presenter)
    fun inject(provider: Provider)
    fun inject(updater: Updater)
    fun inject(usecase: Usecase)
    fun inject(inputAccepter: InputAccepter)
//    fun inject(selectPresenter: SelectPresenter)
}