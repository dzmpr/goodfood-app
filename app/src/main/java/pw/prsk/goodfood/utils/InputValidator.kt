package pw.prsk.goodfood.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class InputValidator(private val field: TextInputLayout, requiredMessage: String? = null) : TextWatcher {
    private var rule: ((v: InputValidator, s: String) -> Boolean)? = null
    private var requiredField: Boolean = false
    private lateinit var emptyInputError: String

    init {
        field.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validate()
            }
        }
        field.editText?.addTextChangedListener(this)

        if (requiredMessage != null) {
            emptyInputError = requiredMessage
            requiredField = true
        }
    }

    fun rule(f: (v: InputValidator, s: String) -> Boolean) {
        rule = f
    }

    fun validate(): Boolean {
        val input = field.editText?.text.toString()
        return validateInput(input)
    }

    private fun validateInput(input: String): Boolean {
        var fieldValid = rule?.let {
            if (it(this, input)) {
                hideError()
                true
            } else {
                false
            }
        } ?: true
        if (requiredField && !requiredFieldRule(input)) {
            showError(emptyInputError)
            fieldValid = false
        }
        if (fieldValid) {
            hideError()
        }
        return fieldValid
    }

    private fun requiredFieldRule(input: String) = input.isNotEmpty()

    fun showError(errorText: String) {
        field.error = errorText
        field.isErrorEnabled = true
    }

    private fun hideError() {
        field.isErrorEnabled = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        validateInput(s.toString())
    }
}