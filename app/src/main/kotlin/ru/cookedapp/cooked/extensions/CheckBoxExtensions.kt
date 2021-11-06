package ru.cookedapp.cooked.extensions

import android.widget.CheckBox
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun CheckBox.checked() = callbackFlow {
    setOnCheckedChangeListener { _, isChecked ->
        trySend(isChecked)
    }
    awaitClose {
        setOnCheckedChangeListener(null)
    }
}
