package pw.prsk.goodfood.utils

import android.view.View
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class AutocompleteSelectionHelper(
    private val field: TextInputLayout,
    private val inputNotMatch: (String) -> Any
) {
    private val textColor = field.editText!!.currentTextColor
    private var selectedItem: Any? = null
    val selected: Any
        get() = if (selectedItem != null) {
            selectedItem!!
        } else {
            inputNotMatch(this.field.editText?.text.toString())
        }

    private val endIconClickListener = View.OnClickListener {
        field.editText?.text?.clear()
        enableInput(true)
        field.setEndIconOnClickListener(null)
    }

    private var itemSelectedCallback: ((Any) -> Unit)? = null

    init {
        // Attach item selected listener
        (field.editText as AutoCompleteTextView).setOnItemClickListener { parent, _, position, _ ->
            selectedItem = parent.getItemAtPosition(position)
            disableInput()
            field.setEndIconOnClickListener(endIconClickListener)
            itemSelectedCallback?.invoke(selected)
        }
        // Hide end icon when item not selected
        field.isEndIconVisible = false
    }

    fun resetSelection() {
        field.editText?.text?.clear()
        enableInput(false)
        field.setEndIconOnClickListener(null)
    }

    fun addItemSelectedCallback(itemSelectedCallback: (Any) -> Unit) {
        this.itemSelectedCallback = itemSelectedCallback
    }

    private fun disableInput() {
        field.apply {
            isEndIconVisible = true
            editText?.isEnabled = false
            editText?.setTextColor(textColor)
            editText?.clearFocus()
        }
    }

    private fun enableInput(focusNeeded: Boolean) {
        field.apply {
            isEndIconVisible = false
            editText?.isEnabled = true
            if (focusNeeded) editText?.requestFocus()
        }
    }
}