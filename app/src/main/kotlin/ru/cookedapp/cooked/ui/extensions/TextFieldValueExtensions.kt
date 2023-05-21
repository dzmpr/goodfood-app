package ru.cookedapp.cooked.ui.extensions

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.Companion.fromText(initialText: String) = TextFieldValue(
    text = initialText,
    selection = TextRange(initialText.length),
)
