package pw.prsk.goodfood

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.data.IngredientWithMeta
import pw.prsk.goodfood.databinding.FragmentAddIngredientBinding
import pw.prsk.goodfood.utils.InputValidator
import pw.prsk.goodfood.viewmodels.EditMealViewModel

class AddIngredientBottomFragment() : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddIngredientBinding

    private val editMealViewModel: EditMealViewModel by viewModels({requireParentFragment()})

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Open dialog with expanded state
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddIngredientBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tilIngredientName.requestFocus()

        val nameValidator = InputValidator(binding.tilIngredientName, resources.getString(R.string.label_name_error))
        val amountValidator = InputValidator(binding.tilAmount, resources.getString(R.string.label_name_error))
        val unitValidator = InputValidator(binding.tilAmountUnit, resources.getString(R.string.label_product_units_error))

        binding.bAddIngredient.setOnClickListener {
            if (nameValidator.validate() and amountValidator.validate() /* and unitValidator.validate() */) {
                editMealViewModel.addIngredient(
                    IngredientWithMeta(
                        0,
                        binding.tilIngredientName.editText?.text.toString(),
                        binding.tilAmount.editText?.text.toString().toFloat(),
                        0,
                        binding.tilAmountUnit.editText?.text.toString()
                    )
                )
                dismiss()
            }
        }

        binding.bAddMore.setOnClickListener {
            if (nameValidator.validate() and amountValidator.validate() /* and unitValidator.validate() */) {
                editMealViewModel.addIngredient(
                    IngredientWithMeta(
                        0,
                        binding.tilIngredientName.editText?.text.toString(),
                        binding.tilAmount.editText?.text.toString().toFloat(),
                        0,
                        binding.tilAmountUnit.editText?.text.toString()
                    )
                )
                // Clear text fields. Should be replaced with animation.
                binding.tilIngredientName.editText?.text?.clear()
                binding.tilAmount.editText?.text?.clear()
                binding.tilAmountUnit.editText?.text?.clear()

                binding.tilIngredientName.requestFocus()

                nameValidator.hideError()
                amountValidator.hideError()
                unitValidator.hideError()
            }
        }
    }
}