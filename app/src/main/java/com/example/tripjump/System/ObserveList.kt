package com.example.tripjump.System

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class ObserveList<T> {
    protected val list = mutableListOf<T>()
    protected val onChange: PublishSubject<Notification<T>> = PublishSubject.create()

    fun add(value: T) {
        list.add(value)
        onChange.onNext(Notification<T>(Operation.ADD, value))
    }

    fun remove(value: T) {
        list.remove(value)
        onChange.onNext(Notification<T>(Operation.REMOVE, value))
    }

    fun getObservable(): Observable<Notification<T>> {
        return onChange
    }
}

data class Notification<T>(val operation: Operation, val Item: T)
enum class Operation {
    ADD,
    REMOVE
}