package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pw.prsk.goodfood.databinding.FragmentRecipeBinding
import pw.prsk.goodfood.viewmodels.RecipeViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

class RecipeFragment: Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get () = _binding!!

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
        viewModel.recipe.observe(viewLifecycleOwner) {
            binding.ctToolbar.title = it.name
            binding.toolbar.title = it.name
            binding.tvDescription.text = it.name
        }

        viewModel.recipePhoto.observe(viewLifecycleOwner) {
            binding.ivRecipePhoto.setImageBitmap(it)
        }
    }

    companion object {
        const val RECIPE_ID_KEY = "recipe_id_key"
    }
}