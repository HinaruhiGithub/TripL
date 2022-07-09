package com.example.tripjump

import com.example.tripjump.Destination.BaseMap.Presenter
import javax.inject.Inject

/**
 * どこからも参照されていないやつをここで全部指定する。
 */
class EntryPoints @Inject constructor(presenter: Presenter, placeCollectionPresenter: com.example.tripjump.Destination.PlaceCollection.Presenter){
}