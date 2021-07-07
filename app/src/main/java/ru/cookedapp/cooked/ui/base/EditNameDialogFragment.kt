package ru.cookedapp.cooked.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.cookedapp.cooked.R

class EditNameDialogFragment : DialogFragment() {
    private lateinit var dialogTitle: String
    private lateinit var initialName: String
    private var dataId: Int = 0

    private var nameTextField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogTitle = it.getString(TITLE_KEY, "")
            initialName = it.getString(INITIAL_NAME_KEY, "")
            dataId = it.getInt(DATA_ID_KEY, 0)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val view = createLayout()
        dialogBuilder.apply {
            setTitle(dialogTitle)
            setView(view)
            setPositiveButton(R.string.label_ok) { _, _ ->
                setResult()
                dismiss()
            }
            setNegativeButton(R.string.label_cancel) { _, _ ->
                dismiss()
            }
        }
        return dialogBuilder.create()
    }

    private fun setResult() {
        val text = nameTextField?.text.toString().trim()
        val result = bundleOf(
            DATA_ID_KEY to dataId,
            RESULT_NAME_KEY to text
        )
        setFragmentResult(RENAME_DIALOG_KEY, result)
    }

    private fun createLayout(): View {
        val container = LinearLayout(context)
        container.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val textField = EditText(context).apply {
            id = View.generateViewId()
            inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            setText(initialName)
            addTextChangedListener {
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                    it.toString().isNotBlank()
            }
        }
        textField.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16f,
                context?.resources?.displayMetrics
            ).toInt()
            setMargins(margin, margin, margin, 0)
        }

        container.addView(textField)
        nameTextField = textField

        return container
    }

    override fun onDestroy() {
        nameTextField = null
        super.onDestroy()
    }

    companion object {
        const val RENAME_DIALOG_KEY = "rename_dialog_key"

        private const val INITIAL_NAME_KEY = "initial_string"
        private const val TITLE_KEY = "title_string"
        const val DATA_ID_KEY = "data_id_int"

        const val RESULT_NAME_KEY = "result_string"

        fun getDialog(titleText: String, initialText: String, dataId: Int): EditNameDialogFragment {
            val dialog = EditNameDialogFragment()
            val data = bundleOf(
                INITIAL_NAME_KEY to initialText,
                TITLE_KEY to titleText,
                DATA_ID_KEY to dataId
            )
            dialog.arguments = data
            return dialog
        }
    }
}
