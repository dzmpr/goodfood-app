package ru.cookedapp.cooked.ui.components.renameDialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import ru.cookedapp.common.extensions.empty
import ru.cookedapp.cooked.ui.extensions.fromText
import ru.cookedapp.cooked.ui.helpers.restore
import ru.cookedapp.cooked.ui.helpers.save

@Stable
internal class RenameDialogState(
    renameSubjectId: Long?,
    textFieldValue: TextFieldValue,
) {

    constructor(
        renameSubjectId: Long?,
        initialText: String,
    ) : this(
        renameSubjectId = renameSubjectId,
        textFieldValue = TextFieldValue.fromText(initialText),
    )

    var renameSubjectId: Long? = renameSubjectId
        private set

    var textFieldValue by mutableStateOf(textFieldValue)
        private set

    fun onValueChanged(value: TextFieldValue) {
        textFieldValue = value
    }

    fun updateState(text: String, subjectId: Long? = null) {
        renameSubjectId = subjectId
        textFieldValue = TextFieldValue.fromText(text)
    }

    fun getStateData(): Pair<String, Long?> = textFieldValue.text to renameSubjectId

    fun requireStateData(): Pair<String, Long> =
        textFieldValue.text to requireNotNull(renameSubjectId)

    companion object {

        fun Saver(): Saver<RenameDialogState, *> = Saver(
            save = {
                arrayListOf(
                    save(it.renameSubjectId),
                    save(it.textFieldValue, TextFieldValue.Saver, this),
                )
            },
            restore = { value: List<*> ->
                RenameDialogState(
                    renameSubjectId = restore(value[0]),
                    textFieldValue = restore(value[1], TextFieldValue.Saver)!!,
                )
            }
        )
    }
}

@Composable
internal fun rememberRenameDialogState(
    initialText: String = String.empty,
    renameSubjectId: Long? = null,
): RenameDialogState = rememberSaveable(
    saver = RenameDialogState.Saver(),
) {
    RenameDialogState(renameSubjectId, initialText)
}
