package com.example.tripjump.DIModule

import com.example.tripjump.Destination.BaseMap.Presenter
import com.example.tripjump.Destination.BaseMap.View
import com.example.tripjump.Destination.PlaceCollection.*
import com.example.tripjump.Destination.Search.*
import com.example.tripjump.EntryPoints
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UniModule {
    @Singleton @Provides fun provideEntryPoints(presenter: Presenter, placeCollectionPresenter: com.example.tripjump.Destination.PlaceCollection.Presenter): EntryPoints = EntryPoints(presenter, placeCollectionPresenter)
    @Singleton @Provides fun provideRequestAcception(provider: Provider): RequestAcception = RequestAcception(provider)
    @Singleton @Provides fun provideView(): View = View()
    @Singleton @Provides fun provideProvider(updater: IProvidePlace): Provider = Provider(updater)
    @Singleton @Provides fun providePresenter(provider: Provider, view: View): Presenter = Presenter(provider, view)
    @Singleton @Provides fun provideData(): Data = Data()
    @Singleton @Provides fun providePlaceCollectionPresenter(view: com.example.tripjump.Destination.PlaceCollection.View, observe: IObserveAddData): com.example.tripjump.Destination.PlaceCollection.Presenter = com.example.tripjump.Destination.PlaceCollection.Presenter(view, observe)
    @Singleton @Provides fun providePlaceCollectionView(): com.example.tripjump.Destination.PlaceCollection.View = com.example.tripjump.Destination.PlaceCollection.View()
}


@Module
abstract class UpdaterModule {
    @Binds abstract fun provideIProvidePlace(updater: Updater): IProvidePlace
}

@Module
abstract class ObserveModule {
    @Binds abstract fun provideIObserveAddData(observe: Observe): IObserveAddData
}
/*
@Module
abstract class MainActivityModule {
    @Binds abstract fun provideIShowBaseMap(activity: MainActivity): IShowBaseMap
    @Binds abstract fun provideISetPlace(activity: MainActivity): ISetPlace
    @Binds abstract fun provideIShowWait(activity: MainActivity): IShowWait
    @Binds abstract fun provideIRequireNowPosition(activity: MainActivity): IRequireNowPosition

    @Binds abstract fun provideITestRequest(requestAcception: RequestAcception) : ITestRequest
}
*/