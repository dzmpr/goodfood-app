package ru.cookedapp.cooked.extensions

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun View.setViewVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@OptIn(ExperimentalCoroutinesApi::class)
fun View.clicks() = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }
    awaitClose {
        setOnClickListener(null)
    }
}
