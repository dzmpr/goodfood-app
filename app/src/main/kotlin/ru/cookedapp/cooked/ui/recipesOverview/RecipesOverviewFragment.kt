package ru.cookedapp.cooked.ui.recipesOverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentRecipesOverviewBinding
import ru.cookedapp.cooked.extensions.setViewVisibility
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.recipeDetails.RecipeDetailsFragment
import ru.cookedapp.cooked.ui.recipeList.RecipeListFragment
import ru.cookedapp.cooked.ui.settings.SettingsBottomFragment

class RecipesOverviewFragment : Fragment() {
    private var _binding: FragmentRecipesOverviewBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipesOverviewViewModel

    private val recipeCallback = object : RecipeClickCallback {
        override fun onFavoriteToggle(recipeId: Long, state: Boolean) {
            viewModel.changeFavoriteState(recipeId, state)
        }

        override fun onRecipeClicked(recipeId: Long) {
            val args = RecipeDetailsFragment.createOpenRecipeBundle(recipeId)
            Navigation.findNavController(requireActivity(), R.id.fcvContainer)
                .navigate(R.id.actionNavigateToRecipe, args)
        }

        override fun onMoreButtonClick(listType: Int) {
            val listTypeKey = when(listType) {
                LIST_ALL_RECIPES -> RecipeListFragment.LIST_TYPE_ALL
                LIST_FAVORITE_RECIPES -> RecipeListFragment.LIST_TYPE_FAVORITES
                LIST_FREQUENT_RECIPES -> RecipeListFragment.LIST_TYPE_FREQUENT
                else -> {
                    throw IllegalStateException("Unknown list type $listType")
                }
            }
            val args = Bundle().apply {
                putInt(RecipeListFragment.LIST_TYPE_KEY, listTypeKey)
            }
            Navigation.findNavController(requireActivity(), R.id.fcvContainer)
                .navigate(R.id.actionNavigateToRecipeList, args)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(RecipesOverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclers()

        viewModel.isDataPresence.observe(viewLifecycleOwner) { hasData ->
            binding.nsvContent.setViewVisibility(hasData)
            binding.tvNoDataPlaceholder.setViewVisibility(!hasData)
        }

        binding.fabAddMeal.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigate(R.id.actionNavigateToEditRecipe)
        }

        binding.bAllRecipes.setOnClickListener {
            val args = Bundle().apply {
                putInt(RecipeListFragment.LIST_TYPE_KEY, RecipeListFragment.LIST_TYPE_ALL)
            }
            Navigation.findNavController(requireActivity(), R.id.fcvContainer)
                .navigate(R.id.actionNavigateToRecipeList, args)
        }

        binding.tbToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionOpenSettings -> {
                    val fragment = SettingsBottomFragment()
                    fragment.show(childFragmentManager, null)
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclers() {
        val frequentAdapter = RecipeCardAdapter(recipeCallback, LIST_FREQUENT_RECIPES)
        CookedApp.appComponent.inject(frequentAdapter)
        viewModel.frequentRecipes.observe(viewLifecycleOwner) {
            frequentAdapter.setList(it)
            binding.groupFrequentRecipes.setViewVisibility(it.isNotEmpty())
        }

        binding.rvFrequentRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = frequentAdapter
            isNestedScrollingEnabled = false
        }

        val favoriteAdapter = RecipeCardAdapter(recipeCallback, LIST_FAVORITE_RECIPES)
        CookedApp.appComponent.inject(favoriteAdapter)
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            favoriteAdapter.setList(it)
            binding.groupFavoriteRecipes.setViewVisibility(it.isNotEmpty())
        }

        binding.rvFavoritesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteAdapter
            isNestedScrollingEnabled = false
        }

        val allRecipesAdapter = RecipeCardAdapter(recipeCallback, LIST_ALL_RECIPES)
        CookedApp.appComponent.inject(allRecipesAdapter)
        viewModel.allRecipes.observe(viewLifecycleOwner) {
            allRecipesAdapter.setList(it)
            binding.groupAllRecipes.setViewVisibility(it.isNotEmpty())
        }

        binding.rvAllRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = allRecipesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface RecipeClickCallback {
        fun onFavoriteToggle(recipeId: Long, state: Boolean)
        fun onRecipeClicked(recipeId: Long)
        fun onMoreButtonClick(listType: Int)
    }

    companion object {
        private const val LIST_ALL_RECIPES = 0
        private const val LIST_FAVORITE_RECIPES = 1
        private const val LIST_FREQUENT_RECIPES = 2
    }
}
