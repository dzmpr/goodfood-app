package pw.prsk.goodfood

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pw.prsk.goodfood.adapters.IngredientAdapter
import pw.prsk.goodfood.databinding.FragmentEditMealBinding
import pw.prsk.goodfood.utils.InputValidator
import pw.prsk.goodfood.viewmodels.EditMealViewModel
import java.io.FileNotFoundException

class EditMealFragment : Fragment() {
    private lateinit var binding: FragmentEditMealBinding

    private val viewModel: EditMealViewModel by viewModels()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(viewModel)
        viewModel.loadProducts()
        viewModel.loadUnits()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            listAdapter.setList(it)
        }

        binding.bAddIngredient.setOnClickListener {
            val sheet = AddIngredientBottomFragment()
            sheet.show(childFragmentManager, null)
        }

        binding.cvRecipePhoto.setOnClickListener {
            showPopup()
        }

        viewModel.photo.observe(viewLifecycleOwner) {
            if (it != null) {
                try {
                    val stream = context?.contentResolver?.openInputStream(it)
                    binding.ivRecipePhoto.setImageDrawable(
                        Drawable.createFromStream(stream, it.toString())
                    )
                } catch (e: FileNotFoundException) {
                    Log.e(DEBUG_TAG, "Selected recipe photo not found. $e")
                }
            } else {
                binding.ivRecipePhoto.setImageDrawable(null)
            }
        }

        viewModel.saveStatus.observe(viewLifecycleOwner) {
            if (it) {
                Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
            }
        }

        val nameValidator = InputValidator(binding.tilRecipeName, context?.getString(R.string.label_name_error))

        binding.bSaveRecipe.setOnClickListener {
            if (nameValidator.validate()) {
                viewModel.saveRecipe(
                    binding.tilRecipeName.editText?.text.toString(),
                    binding.tilDescription.editText?.text.toString()
                )
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
        private const val DEBUG_TAG = "EditMealFragment"
    }
}