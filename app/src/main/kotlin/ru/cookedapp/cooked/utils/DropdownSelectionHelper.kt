package ru.cookedapp.cooked.utils

import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class DropdownSelectionHelper(
    private val field: TextInputLayout,
    private val inputNotMatch: (String) -> Any
) {
    private var selectedItem: Any? = null
    val selected: Any
        get() = if (selectedItem != null) {
            selectedItem!!
        } else {
            inputNotMatch(this.field.editText?.text.toString())
        }

    private var itemSelectedListener: ((Any) -> Unit)? = null

    init {
        // Attach item selected listener
        (field.editText as AutoCompleteTextView).setOnItemClickListener { parent, _, position, _ ->
            selectedItem = parent.getItemAtPosition(position) // Save selected item
            itemSelectedListener?.invoke(selected) // Call item selected listener
        }
    }

    fun resetSelection() {
        selectedItem = null
        field.editText?.text?.clear()
    }

    fun addItemSelectedListener(itemSelectedListener: (Any) -> Unit): DropdownSelectionHelper {
        this.itemSelectedListener = itemSelectedListener
        return this
    }
}
