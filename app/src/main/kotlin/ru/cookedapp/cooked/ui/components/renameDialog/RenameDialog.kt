package ru.cookedapp.cooked.ui.components.renameDialog

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import kotlinx.coroutines.android.awaitFrame
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.ui.helpers.LaunchedEffect

@Composable
internal fun RenameDialog(
    state: RenameDialogState,
    @StringRes titleTextRes: Int,
    @StringRes labelTextRes: Int,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    @StringRes placeholderTextRes: Int? = null,
    onSaved: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val isSavingEnabled by remember { derivedStateOf { state.textFieldValue.text.isNotBlank() } }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect {
        awaitFrame()
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSaved()
                    onDismiss()
                },
                enabled = isSavingEnabled,
            ) {
                Text(text = stringResource(R.string.label_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.label_cancel))
            }
        },
        icon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        title = {
            Text(text = stringResource(titleTextRes))
        },
        text = {
            OutlinedTextField(
                value = state.textFieldValue,
                onValueChange = { state.onValueChanged(it) },
                singleLine = true,
                label = {
                    Text(text = stringResource(labelTextRes))
                },
                placeholder = {
                    placeholderTextRes?.let { textRes ->
                        Text(text = stringResource(textRes))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions {
                    if (isSavingEnabled) {
                        onSaved()
                        onDismiss()
                    }
                },
                modifier = Modifier.focusRequester(focusRequester),
            )
        },
        modifier = modifier,
    )
}
