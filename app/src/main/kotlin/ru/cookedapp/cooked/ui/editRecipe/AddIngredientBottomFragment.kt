package ru.cookedapp.cooked.ui.editRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.IngredientWithMeta
import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.data.db.entity.ProductUnitEntity
import ru.cookedapp.cooked.databinding.FragmentAddIngredientBinding
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.base.BaseBottomSheetFragment
import ru.cookedapp.cooked.ui.base.BaseDropdownAdapter
import ru.cookedapp.cooked.utils.AutocompleteSelectionHelper
import ru.cookedapp.cooked.utils.DropdownSelectionHelper
import ru.cookedapp.cooked.utils.InputValidator

class AddIngredientBottomFragment : BaseBottomSheetFragment() {
    private lateinit var binding: FragmentAddIngredientBinding

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var editRecipeViewModel: EditRecipeViewModel

    private lateinit var selectedUnitHelper: DropdownSelectionHelper
    private lateinit var selectedProductHelper: AutocompleteSelectionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CookedApp.appComponent.inject(this)
        editRecipeViewModel = ViewModelProvider(requireParentFragment(), vmFactory).get(EditRecipeViewModel::class.java)
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
        super.onViewCreated(view, savedInstanceState)
        binding.tilIngredientName.requestFocus()

        val nameValidator = InputValidator(binding.tilIngredientName, rp.getString(R.string.label_name_error))
        val amountValidator = InputValidator(binding.tilAmount, rp.getString(R.string.label_name_error))
        val unitValidator = InputValidator(binding.tilAmountUnit, rp.getString(R.string.label_product_units_error))

        selectedProductHelper = AutocompleteSelectionHelper(binding.tilIngredientName) { input ->
            ProductEntity(id = 0, name = input)
        }.addItemSelectedListener {
            // Set focus to next text field
            binding.tilAmount.requestFocus()
        }

        selectedUnitHelper = DropdownSelectionHelper(binding.tilAmountUnit) { input ->
            ProductUnitEntity(id = 0, name = input)
        }

        binding.bAddIngredient.setOnClickListener {
            if (nameValidator.validate() and amountValidator.validate() and unitValidator.validate()) {
                editRecipeViewModel.addIngredient(getIngredient())
                dismiss()
            }
        }

        binding.bAddMore.setOnClickListener {
            if (nameValidator.validate() and amountValidator.validate() and unitValidator.validate()) {
                editRecipeViewModel.addIngredient(getIngredient())
                // Clear itemText fields. Should be replaced with animation.
                binding.tilAmount.editText?.text?.clear()
                binding.tilAmountUnit.editText?.text?.clear()

                selectedProductHelper.resetSelection(true)

                amountValidator.hideError()
                unitValidator.hideError()
            }
        }

        editRecipeViewModel.unitsList.observe(viewLifecycleOwner) { it ->
            val adapter = BaseDropdownAdapter(requireContext(), R.layout.dropdown_item, it) { item ->
                item.name
            }
            (binding.tilAmountUnit.editText as AutoCompleteTextView).setAdapter(adapter)
        }

        editRecipeViewModel.productsList.observe(viewLifecycleOwner) {
            val adapter = ProductAutocompleteAdapter(requireContext(), R.layout.dropdown_item, it)
            (binding.tilIngredientName.editText as AutoCompleteTextView).setAdapter(adapter)
        }
    }

    private fun getIngredient(): IngredientWithMeta = IngredientWithMeta(
        selectedProductHelper.selected as ProductEntity,
        binding.tilAmount.editText?.text.toString().toFloat(),
        selectedUnitHelper.selected as ProductUnitEntity
    )
}
