package pw.prsk.goodfood

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
import pw.prsk.goodfood.adapters.RecipeAdapter
import pw.prsk.goodfood.databinding.FragmentRecipesOverviewBinding
import pw.prsk.goodfood.viewmodels.RecipesOverviewViewModel
import javax.inject.Inject

class RecipesOverviewFragment : Fragment() {
    private var _binding: FragmentRecipesOverviewBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipesOverviewViewModel

    private val recipeCallback = object : onRecipeItemClicked {
        override fun onFavoriteToggle(recipeId: Int, state: Boolean) {
            viewModel.changeFavoriteState(recipeId, state)
        }

        override fun onRecipeClicked(recipeId: Int) {
            Toast.makeText(this@RecipesOverviewFragment.context, "Clicked at $recipeId recipe.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[RecipesOverviewViewModel::class.java]
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

        viewModel.isDataPresence.observe(viewLifecycleOwner) {
            if (it) {
                binding.nsvContent.visibility = View.VISIBLE
                binding.tvNoDataPlaceholder.visibility = View.GONE
            } else {
                binding.nsvContent.visibility = View.GONE
                binding.tvNoDataPlaceholder.visibility = View.VISIBLE
            }
        }

        binding.fabAddMeal.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigate(R.id.add_meal_flow)
        }
    }

    private fun initRecyclers() {
        val frequentAdapter = RecipeAdapter(recipeCallback)
        (requireActivity().application as MyApplication).appComponent.inject(frequentAdapter)
        viewModel.frequentRecipes.observe(viewLifecycleOwner) {
            frequentAdapter.setList(it)
            binding.groupFrequentRecipes.visibility = when {
                it.isEmpty() -> View.GONE
                else -> View.VISIBLE
            }
        }

        binding.rvFrequentRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = frequentAdapter
        }

        val favoriteAdapter = RecipeAdapter(recipeCallback)
        (requireActivity().application as MyApplication).appComponent.inject(favoriteAdapter)
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            favoriteAdapter.setList(it)
            binding.groupFavoriteRecipes.visibility = when {
                it.isEmpty() -> View.GONE
                else -> View.VISIBLE
            }
        }

        binding.rvFavoritesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteAdapter
        }

        val allRecipesAdapter = RecipeAdapter(recipeCallback)
        (requireActivity().application as MyApplication).appComponent.inject(allRecipesAdapter)
        viewModel.allRecipes.observe(viewLifecycleOwner) {
            allRecipesAdapter.setList(it)
            binding.groupAllRecipes.visibility = when {
                it.isEmpty() -> View.GONE
                else -> View.VISIBLE
            }
        }

        binding.rvAllRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = allRecipesAdapter
        }
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface onRecipeItemClicked {
        fun onFavoriteToggle(recipeId: Int, state: Boolean)
        fun onRecipeClicked(recipeId: Int)
    }
}