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
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.databinding.FragmentAddProductBinding
import pw.prsk.goodfood.utils.InputValidator
import pw.prsk.goodfood.viewmodels.RecipeListViewModel

class AddProductBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddProductBinding

    // TODO: Mb refactor sharedViewModel
    private val viewModel: RecipeListViewModel by viewModels({ requireParentFragment() })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
        binding = FragmentAddProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameValidator = InputValidator(
            binding.tilProductName,
            resources.getString(R.string.label_name_error)
        )

        binding.tilProductName.requestFocus()

        binding.bAddProduct.setOnClickListener {
            if (nameValidator.validate()) {
//                viewModel.addProduct(Product(name = binding.tilProductName.editText?.text.toString()))
                dismiss()
            }
        }
    }
}