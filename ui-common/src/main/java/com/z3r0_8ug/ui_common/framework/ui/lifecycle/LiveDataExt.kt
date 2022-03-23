package com.z3r0_8ug.ui_common.framework.ui.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.lifecycle.map
import com.z3r0_8ug.ui_common.framework.ui.InputData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * A simple helper to check for `null` and empty values
 * if the type can be checked for empty
 */
private fun Any?.isNullOrEmptySequence(): Boolean {
  if (this == null) {
    return true
  }

  if (this is CharSequence) {
    return isEmpty()
  }

  return false
}

private val liveDataNotSet by lazy {
  // Get the object that indicates no value
  val notSetField = LiveData::class.java.getDeclaredField("NOT_SET")
  notSetField.isAccessible = true
  notSetField.get(null)
}

fun <T> liveDataOf(value: T): LiveData<T> {
  return liveData { emit(value) }
}

/**
 * Determines if the [LiveData] has data that was specified either through
 * the constructor or through a `setValue` call.
 *
 * @return `true` if data has been set in the [LiveData]
 */
private fun <T> LiveData<T>.hasDataSet(includePending: Boolean = false): Boolean {
  // Check if the data is set
  val dataField = LiveData::class.java.getDeclaredField("mData")
  dataField.isAccessible = true
  val dataSet = dataField.get(this)!= liveDataNotSet
  if (dataSet || !includePending) {
    return dataSet
  }

  // Check if there is pending data
  val pendingDataField = LiveData::class.java.getDeclaredField("mPendingData")
  pendingDataField.isAccessible = true
  return pendingDataField.get(this)!= liveDataNotSet
}

/**
 * Transforms the [MutableLiveData] in to a [LiveData] of [InputData] to be used for
 * input fields (TextFields). This is an extension of [asInputData] specifically for
 * strings to add verification of empty fields
 */
@MainThread
fun <T> MutableLiveData<T?>.asInputData(
  allowEmpty: Boolean,
  modificationsAllowed: Boolean = true,
  onValueChange: (MutableLiveData<T?>, T?) -> Unit = { ld, value -> ld.postValue(value) },
  errorTransform: (T?) -> Boolean = { false }
): LiveData<InputData<T?>> {
  val dataEntered = AtomicBoolean(false)
  return asInputData(modificationsAllowed = modificationsAllowed, onValueChange = onValueChange) {
    // Keep track of data having been entered
    // In most cases we don't want to show the error when the user hasn't input any information
    dataEntered.set(dataEntered.get() || !it.isNullOrEmptySequence())

    // Check the empty-ness, then perform the requested transform
    (it.isNullOrEmptySequence() && dataEntered.get() && !allowEmpty) || errorTransform(it)
  }
}

/**
 * Transforms the [MutableLiveData] in to a [LiveData] of [InputData] to be used for
 * input fields (TextFields).
 *
 * @param onValueChange Called when the host is requesting an update to the value, by default this will
 *                      post the requested value to the source [MutableLiveData]
 * @param modificationsAllowed Prevents the field from being modified
 * @param errorTransform Called on value changes, performs the determination if the new value has an error.
 *                       By default this will return `false`
 */
@MainThread
fun <T> MutableLiveData<T>.asInputData(
  onValueChange: (MutableLiveData<T>, T) -> Unit = { ld, value -> ld.postValue(value) },
  modificationsAllowed: Boolean = true,
  errorTransform: (T) -> Boolean = { false }
): LiveData<InputData<T>> {
  return map { newValue ->
    InputData(
      value = newValue,
      onValueChange = {
        onValueChange.invoke(this@asInputData, it)
      },
      hasError = errorTransform(newValue),
      modificationsAllowed = modificationsAllowed,
    )
  }
}

/**
 * A simple map operation that allows us to specify the [CoroutineContext] used
 * to perform the mapping operation.
 */
@MainThread
inline fun <T, R> LiveData<T>.map(context: CoroutineContext = EmptyCoroutineContext, crossinline transform: (T) -> R): LiveData<R> {
  val scope = CoroutineScope(Dispatchers.Main.immediate + context)
  return MediatorLiveData<R>().apply {
    addSource(this@map) {
      scope.launch {
        postValue(transform(it))
      }
    }
  }
}

@MainThread
@Suppress("UNCHECKED_CAST")
fun <T, FIRST, RESULT> LiveData<T>.merge(
  first: LiveData<FIRST>,
  transform: (T, FIRST) -> RESULT
): MutableLiveData<RESULT> {
  return MediatorLiveData<RESULT>().apply {
    addSource(this@merge) { parentValue ->
      if (first.hasDataSet()) {
        value = transform(parentValue, first.value as FIRST)
      }
    }

    addSource(first) { firstValue ->
      if (this@merge.hasDataSet()) {
        value = transform(this@merge.value as T, firstValue)
      }
    }
  }
}

@MainThread
@Suppress("UNCHECKED_CAST")
fun <T, FIRST, SECOND, RESULT> LiveData<T>.merge(
  first: LiveData<FIRST>,
  second: LiveData<SECOND>,
  transform: (T, FIRST, SECOND) -> RESULT
): MutableLiveData<RESULT> {
  return MediatorLiveData<RESULT>().apply {
    addSource(this@merge) { parentValue ->
      if (first.hasDataSet() && second.hasDataSet()) {
        value = transform(parentValue, first.value as FIRST, second.value as SECOND)
      }
    }

    addSource(first) { firstValue ->
      if (this@merge.hasDataSet() && second.hasDataSet()) {
        value = transform(this@merge.value as T, firstValue, second.value as SECOND)
      }
    }

    addSource(second) { secondValue ->
      if (this@merge.hasDataSet() && first.hasDataSet()) {
        value = transform(this@merge.value as T, first.value as FIRST, secondValue)
      }
    }
  }
}

@MainThread
@Suppress("UNCHECKED_CAST")
fun <T, FIRST, SECOND, THIRD, RESULT> LiveData<T>.merge(
  first: LiveData<FIRST>,
  second: LiveData<SECOND>,
  third: LiveData<THIRD>,
  transform: (T, FIRST, SECOND, THIRD) -> RESULT
): MutableLiveData<RESULT> {
  return MediatorLiveData<RESULT>().apply {
    addSource(this@merge) { parentValue ->
      if (first.hasDataSet() && second.hasDataSet()) {
        value = transform(parentValue, first.value as FIRST, second.value as SECOND, third.value as THIRD)
      }
    }

    addSource(first) { firstValue ->
      if (this@merge.hasDataSet() && second.hasDataSet() && third.hasDataSet()) {
        value = transform(this@merge.value as T, firstValue, second.value as SECOND, third.value as THIRD)
      }
    }

    addSource(second) { secondValue ->
      if (this@merge.hasDataSet() && first.hasDataSet() && third.hasDataSet()) {
        value = transform(this@merge.value as T, first.value as FIRST, secondValue, third.value as THIRD)
      }
    }

    addSource(third) { thirdValue ->
      if (this@merge.hasDataSet() && first.hasDataSet() && second.hasDataSet()) {
        value = transform(this@merge.value as T, first.value as FIRST, second.value as SECOND, thirdValue)
      }
    }
  }
}

/**
 * Immediately sets the value of the [LiveData] to the [initialValue]. This is useful
 * to set the value of a [LiveData] that is a combination of multiple such as a
 * [MediatorLiveData] or the result of [merge].
 *
 * **NOTE:**
 * This could be performed with an `apply { value = initialValue }` instead
 * however this was added for clarity.
 */
@MainThread
fun <T, L : MutableLiveData<T>> L.startWith(initialValue: T): L {
  value = initialValue
  return this
}

/**
 * Returns a [MutableLiveData] that is a combination of the original (`this`) [MutableLiveData] and the
 * [startSource] [LiveData]. Any changes that are applied to the returned [MutableLiveData] will be
 * represented (forwarded) to the original (`this`) while any changes from the [startSource] are only
 * applied if the original (`this`) hasn't had a value defined.
 *
 * **NOTE:**
 * If you define a default/starting value for the original (`this`) [MutableLiveData] then the values
 * emitted by [startSource] will never be applied.
 *
 * **Example:**
 * ```
 * val savedState: SavedStateHandle // copied for the example
 * val asset: LiveData<Asset>() // copied for the example
 * val name = savedState.getLiveData<String>("nickname").startWith(asset) {
 *     it?.name ?: ""
 * }
 * ```
 *
 * @param startSource The [LiveData] that the first emission will be used as the starting value
 *                    if no value was previously specified.
 * @param ignoreNull Defines if a `null` value emission from [startSource] should be ignored
 * @param transform The transformation function called with the value from the [startSource].
 *                  This is only called if a value wasn't previously specified.
 */
@MainThread
fun <T, I> MutableLiveData<T>.startWith(startSource: LiveData<I>, ignoreNull: Boolean = false, transform: (I) -> T): MutableLiveData<T> {
  return CallbackLiveData<T>(
    onValueSet = {
      this.value = it
    }
  ).apply {
    addSource(this@startWith) { newValue ->
      setValue(newValue)
    }

    addSource(startSource) { startValue ->
      // Only applies the startValue if data hasn't already been defined
      if (!hasDataSet(includePending = true)) {
        if (!ignoreNull || startValue!=null) {
          setValue(transform(startValue))
        }
      }
    }
  }
}

@MainThread
fun <T> MutableLiveData<T>.startWith(startSource: LiveData<T>, ignoreNull: Boolean = false): MutableLiveData<T> {
  return startWith(startSource, ignoreNull) { it }
}

/**
 * A copy of the `runningFold` method that stores the accumulator in the
 * [savedStateHandle] with the given [key].
 *
 * NOTE: The type `R` must be able to be stored in a [SavedStateHandle]
 */
@MainThread
fun <T, R> LiveData<T>.runningFoldSaveable(
  savedStateHandle: SavedStateHandle,
  key: String,
  initial: R,
  operation: (accumulator: R, value: T) -> R
): MutableLiveData<R> {
  return CallbackLiveData<R>(
    onValueSet = {
      savedStateHandle.set(key, it)
    }
  ).apply {
    postValue(savedStateHandle.get(key) ?: initial)

    addSource(this@runningFoldSaveable) { value ->
      val accumulator: R = savedStateHandle.get(key) ?: initial
      setValue(operation(accumulator, value))
    }
  }
}

/**
 * A simple transformation that allows for running side effects on each emission
 */
fun <T> LiveData<T>.onEach(action: (T) -> Unit): LiveData<T> {
  return map {
    it.also(action)
  }
}

/**
 * A [MediatorLiveData] that informs the [onValueSet] callback when the value is updated.
 */
private class CallbackLiveData<T>(private val onValueSet: (T) -> Unit): MediatorLiveData<T>() {
  private val notifyingChange = AtomicBoolean(false)

  override fun setValue(value: T) {
    super.setValue(value)

    if (!notifyingChange.getAndSet(true)) {
      onValueSet(value)
      notifyingChange.set(false)
    }
  }
}

/**
 * A [MediatorLiveData] that informs the [onValueSet] callback when the value is updated.
 *
 * This is needed because the `startWith` depends on the bug to work properly, (notifyingChange check
 * should be !-ed). To fix an issue in the runningFoldSaveable we've forked the code until we can
 * revisit this logic. I'm pushing it off due to the discussions around migrating away from LiveData
 * to just using Flow now that we aren't constrained to
 */
@Deprecated("Use CallbackLiveData as this contains a known bug")
private class BuggyCallbackLiveData<T>(private val onValueSet: (T) -> Unit): MediatorLiveData<T>() {
  private val notifyingChange = AtomicBoolean(false)

  override fun setValue(value: T) {
    super.setValue(value)

    // NOTE: this should be !notifyingChange
    if (notifyingChange.getAndSet(true)) {
      onValueSet(value)
      notifyingChange.set(false)
    }
  }
}