package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import pw.prsk.goodfood.databinding.FragmentRecipeBinding
import pw.prsk.goodfood.viewmodels.RecipeViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(RecipeViewModel::class.java)

        handleArguments()
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

        viewModel.recipe.observe(viewLifecycleOwner) {
//            binding.ctToolbar.title = it.name
//            binding.toolbar.title = it.name
        }

        viewModel.recipePhoto.observe(viewLifecycleOwner) {
//            binding.ivRecipePhoto.setImageBitmap(it)
        }
    }

    private fun setupToolbar() {
        binding.tbToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }

        binding.tbToolbar.setOnMenuItemClickListener {
            Toast.makeText(this.context, "Test menu", Toast.LENGTH_SHORT).show()
            true
        }
    }

    companion object {
        const val RECIPE_ID_KEY = "recipe_id_key"
    }
}