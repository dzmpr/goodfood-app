package ru.cookedapp.cooked.ui.helpers

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.LaunchedEffect as ComposeLaunchedEffect

@Composable
internal fun LaunchedEffect(
    block: suspend CoroutineScope.() -> Unit,
) = ComposeLaunchedEffect(key1 = Unit, block = block)
