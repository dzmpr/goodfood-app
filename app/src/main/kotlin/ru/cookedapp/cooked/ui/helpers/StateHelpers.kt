package ru.cookedapp.cooked.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
internal fun <T : Any> rememberSaveableState(
    value: T,
    saver: Saver<MutableState<T>, out Any> = autoSaver(),
): MutableState<T> = rememberSaveable(saver = saver) { mutableStateOf(value) }
