package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.fabAddMeal.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigate(R.id.add_meal_flow)
        }
    }

    private fun initRecyclers() {
        val frequentAdapter = RecipeAdapter()
        viewModel.frequentRecipes.observe(viewLifecycleOwner) {
            frequentAdapter.setList(it)
        }

        binding.rvFrequentRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = frequentAdapter
        }

        val favoriteAdapter = RecipeAdapter()
        viewModel.favoriteRecipes.observe(viewLifecycleOwner) {
            favoriteAdapter.setList(it)
        }

        binding.rvFavoritesList.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteAdapter
        }

        val allRecipesAdapter = RecipeAdapter()
        viewModel.allRecipes.observe(viewLifecycleOwner) {
            allRecipesAdapter.setList(it)
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
}