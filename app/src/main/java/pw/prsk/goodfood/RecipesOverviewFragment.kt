package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import pw.prsk.goodfood.adapters.RecipeAdapter
import pw.prsk.goodfood.databinding.FragmentRecipesOverviewBinding
import pw.prsk.goodfood.utils.ItemSwipeDecorator
import pw.prsk.goodfood.utils.RecipeItemTouchHelperCallback
import pw.prsk.goodfood.viewmodels.RecipesOverviewViewModel

class RecipesOverviewFragment : Fragment() {
    private var _binding: FragmentRecipesOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipesOverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMealsList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMealList()

        viewModel.deleteSnack.observe(viewLifecycleOwner) {
            val message = resources.getString(R.string.snackbar_item_deleted, it)
            showSnackbar(message)
        }

        binding.fabAddMeal.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigate(R.id.add_meal_flow)
        }
    }

    private fun initMealList() {
        val mealAdapter = RecipeAdapter()
        subscribeUi(mealAdapter)

        binding.rvMealsList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = mealAdapter
        }

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder()
            .setRightSideIcon(R.drawable.ic_trash_bin, R.color.ivory)
            .setBackgroundColor(rightSideColorId = R.color.rose_madder)
            .setIconMargin(50)
            .setRightSideText(R.string.delete_action_label, R.color.ivory, 16f)
            .getDecorator()

        val ithCallback = RecipeItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvMealsList)
    }

    private fun subscribeUi(adapter: RecipeAdapter) {
        viewModel.recipeList.observe(viewLifecycleOwner) { meals ->
            adapter.setList(meals)
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