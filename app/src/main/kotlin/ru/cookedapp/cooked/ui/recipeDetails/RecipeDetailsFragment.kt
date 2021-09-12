package ru.cookedapp.cooked.ui.recipeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentRecipeBinding
import ru.cookedapp.cooked.extensions.setViewVisibility
import ru.cookedapp.cooked.ui.CookedApp

class RecipeDetailsFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(RecipeDetailsViewModel::class.java)

        if (savedInstanceState == null) {
            handleArguments()
        }
    }

    private fun handleArguments() {
        if (arguments != null) {
            val recipeId = requireArguments().getInt(RECIPE_ID_KEY)
            viewModel.loadRecipe(recipeId)
        } else {
            throw IllegalStateException("Recipe id should be provided.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()

        val listAdapter = IngredientListAdapter()
        binding.rvIngredientsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        viewModel.ingredientsList.observe(viewLifecycleOwner) {
            binding.groupIngredientsSection.setViewVisibility(it.isNotEmpty())
            binding.fabAddToCart.setViewVisibility(it.isNotEmpty())
            listAdapter.setList(it)
        }

        viewModel.recipe.observe(viewLifecycleOwner) {
            binding.tvRecipeName.text = it.name
            binding.tvRecipeCategory.text = it.category.name
            binding.tvRecipeInstructions.text = it.description
            binding.cbFavorites.isChecked = it.inFavorites
            binding.tvLastCooked.text = if (it.lastCooked.isEqual(LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault()))) {
                requireContext().resources.getString(R.string.label_last_cooked, getString(R.string.label_never))
            } else {
                val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
                requireContext().resources.getString(R.string.label_last_cooked, it.lastCooked.format(formatter))
            }
            listAdapter.setBaseCount(it.servingsNum)
        }

        viewModel.recipePhoto.observe(viewLifecycleOwner) {
            binding.ivRecipePhoto.setImageBitmap(it)
        }

        viewModel.recipeDeleteAction.observe(viewLifecycleOwner) {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }

        viewModel.servingsNum.observe(viewLifecycleOwner) {
            listAdapter.setSelectedCount(it)
            binding.tvServings.text = it.toString()
        }

        viewModel.cartEvent.observe(viewLifecycleOwner) {
            if (it) {
                showSnackbar(requireContext().getString(R.string.label_added_to_cart))
            }
        }

        binding.bDecrease.setOnClickListener {
            viewModel.onDecreaseClicked()
        }

        binding.bIncrease.setOnClickListener {
            viewModel.onIncreaseClicked()
        }

        binding.cbFavorites.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeFavoriteState(isChecked)
        }

        binding.fabAddToCart.setOnClickListener {
            viewModel.addIngredientsToCart()
        }
    }

    private fun setupToolbar() {
        binding.tbToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }

        binding.tbToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionEditRecipe -> {
                    Navigation.findNavController(requireActivity(),
                        R.id.fcvContainer
                    ).navigate(R.id.actionNavigateToEditRecipe)
                    true
                }
                R.id.actionMarkCooked -> {
                    viewModel.markAsCooked()
                    Toast.makeText(this.context, "Marked as cooked!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.actionDeleteRecipe -> {
                    viewModel.deleteRecipe()
                    Toast.makeText(this.context, "Recipe deleted.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val RECIPE_ID_KEY = "recipe_id_key"
    }
}
