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
        resetSelection(true)
    }

    private var itemSelectedListener: ((Any) -> Unit)? = null

    init {
        // Attach item selected listener
        (field.editText as AutoCompleteTextView).setOnItemClickListener { parent, _, position, _ ->
            selectedItem = parent.getItemAtPosition(position) // Save selected item
            itemSelectedListener?.invoke(selected) // Call item selected listener
            disableInput()
            field.setEndIconOnClickListener(endIconClickListener) // Add end icon click listener
        }
        // Initially hide end icon
        field.isEndIconVisible = false
    }

    fun resetSelection(focusNeeded: Boolean) {
        selectedItem = null
        field.editText?.text?.clear()
        enableInput(focusNeeded)
        field.setEndIconOnClickListener(null)
    }

    fun addItemSelectedListener(itemSelectedListener: (Any) -> Unit): AutocompleteSelectionHelper {
        this.itemSelectedListener = itemSelectedListener
        return this
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
            isErrorEnabled = false
            isEndIconVisible = false
            editText?.isEnabled = true
            if (focusNeeded) editText?.requestFocus()
        }
    }
}