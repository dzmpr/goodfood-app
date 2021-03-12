package pw.prsk.goodfood

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pw.prsk.goodfood.adapters.IngredientAdapter
import pw.prsk.goodfood.adapters.RecipeCategoryAutocompleteAdapter
import pw.prsk.goodfood.data.RecipeCategory
import pw.prsk.goodfood.databinding.FragmentEditRecipeBinding
import pw.prsk.goodfood.utils.AutocompleteSelectionHelper
import pw.prsk.goodfood.utils.InputValidator
import pw.prsk.goodfood.viewmodels.EditRecipeViewModel

class EditRecipeFragment : Fragment() {
    private lateinit var binding: FragmentEditRecipeBinding

    private val viewModel: EditRecipeViewModel by viewModels()

    private val getPhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.setPhotoUri(newPhotoUri!!)
        } else {
            viewModel.removePhoto()
        }
    }

    private val pickPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setPhotoUri(it)
    }

    private var newPhotoUri: Uri? = null

    private lateinit var categorySelectHelper: AutocompleteSelectionHelper

    private var servingsCount: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            (requireActivity().application as MyApplication).appComponent.inject(viewModel)
            viewModel.loadProducts()
            viewModel.loadUnits()
            viewModel.loadCategories()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initIngredientsRecycler()
        subscribeUi()

        binding.tbToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }

        binding.bAddIngredient.setOnClickListener {
            val sheet = AddIngredientBottomFragment()
            sheet.show(childFragmentManager, null)
        }

        binding.cvRecipePhoto.setOnClickListener {
            showPopup()
        }

        // Set default slider value to TextView
        binding.tvServings.text = binding.sServings.value.toInt().toString()
        binding.sServings.addOnChangeListener { _, value, _ ->
            servingsCount = value.toInt()
            binding.tvServings.text = value.toInt().toString()
        }

        categorySelectHelper = AutocompleteSelectionHelper(binding.tilRecipeCategory) { input ->
            RecipeCategory(name = input)
        }

        val nameValidator = InputValidator(binding.tilRecipeName, context?.getString(R.string.label_name_error))

        binding.bSaveRecipe.setOnClickListener {
            val description = if (binding.tilDescription.editText?.text.toString().isEmpty()) {
                null
            } else {
                binding.tilDescription.editText?.text.toString()
            }
            if (nameValidator.validate()) {
                viewModel.saveRecipe(
                    binding.tilRecipeName.editText?.text.toString(),
                    description,
                    servingsCount,
                    categorySelectHelper.selected as RecipeCategory?
                )
            }
        }
    }

    private fun initIngredientsRecycler() {
        val listAdapter = IngredientAdapter()

        binding.rvIngredientsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(this.context,
                    LinearLayoutManager.VERTICAL)
            )
        }

        viewModel.ingredients.observe(viewLifecycleOwner) {
            binding.groupPlaceholder.visibility = if (it.isNotEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            listAdapter.setList(it)
        }
    }

    private fun subscribeUi() {
        viewModel.photo.observe(viewLifecycleOwner) {
            binding.ivRecipePhoto.setImageDrawable(it)
        }

        viewModel.recipeCategories.observe(viewLifecycleOwner) {
            val adapter = RecipeCategoryAutocompleteAdapter(requireContext(), R.layout.dropdown_item, it)
            (binding.tilRecipeCategory.editText as AutoCompleteTextView).setAdapter(adapter)
        }

        viewModel.saveStatus.observe(viewLifecycleOwner) {
            if (it) {
                Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
            }
        }
    }

    private fun showPopup() {
        PopupMenu(context, binding.ivRecipePhotoPlaceholder).apply {
            inflate(R.menu.menu_add_recipe_photo)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionChoosePhoto -> {
                        pickPhoto()
                        true
                    }
                    R.id.actionTakePhoto -> {
                        takePhoto()
                        true
                    }
                    R.id.actionRemovePhoto -> {
                        removePhoto()
                        true
                    }
                    else -> false
                }
            }
            /* Check photo state */
            if (viewModel.photoStatus) {
                menu.setGroupVisible(R.id.groupAddPhoto, false)
                menu.setGroupVisible(R.id.groupRemovePhoto, true)
            } else {
                menu.setGroupVisible(R.id.groupAddPhoto, true)
                menu.setGroupVisible(R.id.groupRemovePhoto, false)
            }
            show()
        }
    }

    private fun takePhoto() {
        newPhotoUri = viewModel.getPhotoUri()
        getPhoto.launch(newPhotoUri)
    }

    private fun pickPhoto() {
        pickPhoto.launch("image/*")
    }

    private fun removePhoto() {
        viewModel.removePhoto()
    }

    companion object {
        private const val TAG = "EditMealFragment"
    }
}