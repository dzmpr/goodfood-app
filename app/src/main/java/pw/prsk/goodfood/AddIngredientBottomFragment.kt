package pw.prsk.goodfood

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.databinding.FragmentAddIngredientBinding
import pw.prsk.goodfood.utils.InputValidator

class AddIngredientBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddIngredientBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Open dialog with expanded state
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
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
//        val nameValidator = InputValidator(binding.tilMealName, resources.getString(R.string.label_name_error))
//
//        binding.bAddMeal.setOnClickListener {
//            if (nameValidator.validate()) {
//                viewModel.addMeal(
//                    Meal(
//                        null,
//                        binding.tilMealName.editText?.text.toString(),
//                        binding.tilDescription.editText?.text.toString(),
//                        LocalDateTime.now(),
//                        0,
//                        0
//                    )
//                )
//                dismiss()
//            }
//        }
    }
}