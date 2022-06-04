package com.z3r08ug.chillspace.ui.shared

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Monitors the lifecycle of the application to perform
 * state based functionality.
 */
@Singleton
class LifecycleMonitor @Inject constructor(
//  private val householdService: HouseholdService
): LifecycleEventObserver {

  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//    if (event == Lifecycle.Event.ON_START) {
//      source.lifecycleScope.launch(Dispatchers.IO) {
//        householdService.findAccountHousehold().firstOrNull()?.let { household ->
//          householdService.refreshData(household.id, true)
//        }
//      }
//    }
  }
}