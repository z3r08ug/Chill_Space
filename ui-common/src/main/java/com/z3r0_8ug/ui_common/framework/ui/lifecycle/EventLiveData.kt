package com.z3r0_8ug.ui_common.framework.ui.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A custom [MutableLiveData] that  only emits to observers when a change occurs instead of
 * emitting the value when new observers are registered. This is useful in situations where
 * we want to observe an event as it occurs such as a `ViewModel` telling the `View`
 * that it has completed some action such as deletion. This is typically used to
 * navigate away from the view so should only be emitted once.
 */
class EventLiveData<T>: MutableLiveData<T>() {
    private val observers = mutableListOf<EventObserver<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (observers.any { it.observer === observer }) {
            return
        }

        val eventObserver = EventObserver(observer)
        observers.add(eventObserver)
        super.observe(owner, eventObserver)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        val eventObserver = if (observer is EventObserver) {
            observer
        } else {
            observers.find { it.observer == observer }
        }

        observers.remove(eventObserver)
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T) {
        observers.forEach { it.expectData() }
        super.setValue(t)
    }

    /**
     * Wraps an [Observer] so that we can keep track of if we should be
     * emitting data when `onChanged` is triggered. This is really what
     * allows us to filter out the unwanted emissions for "event" data
     */
    private class EventObserver<T>(val observer: Observer<T>) : Observer<T> {
        private var expectingData = AtomicBoolean(false)

        override fun onChanged(t: T?) {
            if (expectingData.getAndSet(false)) {
                observer.onChanged(t)
            }
        }

        fun expectData() {
            expectingData.set(true)
        }
    }
}