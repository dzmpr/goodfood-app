package pw.prsk.goodfood

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.adapters.ProductCategoryAdapter
import pw.prsk.goodfood.adapters.ProductUnitAdapter
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.data.ProductCategory
import pw.prsk.goodfood.data.ProductUnit
import pw.prsk.goodfood.databinding.FragmentAddProductBinding
import pw.prsk.goodfood.utils.InputValidator
import pw.prsk.goodfood.viewmodels.ProductsViewModel

class AddProductBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddProductBinding

    // TODO: Mb refactor sharedViewModel
    private val viewModel: ProductsViewModel by activityViewModels()

    private lateinit var categories: List<ProductCategory>
    private var selectedCategoryId: Int = -1

    private lateinit var units: List<ProductUnit>
    private var selectedUnitId: Int = -1

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCategories()
        viewModel.loadUnits()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Attach dropdown list adapters
        viewModel.categoriesList.observe(viewLifecycleOwner) {
            categories = it
            val adapter =
                ProductCategoryAdapter(requireContext(), R.layout.dropdown_item, categories)
            (binding.tilCategoryDropdown.editText as AutoCompleteTextView).setAdapter(adapter)
        }

        viewModel.unitsList.observe(viewLifecycleOwner) {
            units = it
            val adapter = ProductUnitAdapter(requireContext(), R.layout.dropdown_item, units)
            (binding.tilUnitDropdown.editText as AutoCompleteTextView).setAdapter(adapter)
        }

        // Get selected category id
        (binding.tilCategoryDropdown.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            selectedCategoryId = categories[position].id!!
        }
        // Get selected unit id
        (binding.tilUnitDropdown.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            selectedUnitId = units[position].id!!
        }

        val nameValidator = InputValidator(
            binding.tilProductName,
            resources.getString(R.string.label_name_error)
        )
        val categoryValidator = InputValidator(
            binding.tilCategoryDropdown,
            resources.getString(R.string.label_category_error)
        )
        val unitValidator = InputValidator(
            binding.tilUnitDropdown,
            resources.getString(R.string.label_product_units_error)
        )

        binding.bAddProduct.setOnClickListener {
            if (nameValidator.validate() and
                categoryValidator.validate() and
                unitValidator.validate()
            ) {
                viewModel.addProduct(
                    Product(
                        null,
                        binding.tilProductName.editText?.text.toString(),
                        selectedUnitId,
                        selectedCategoryId
                    )
                )
                dismiss()
            }
        }
    }
}