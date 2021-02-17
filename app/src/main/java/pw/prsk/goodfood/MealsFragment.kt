package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.snackbar.Snackbar

import pw.prsk.goodfood.adapters.MealAdapter
import pw.prsk.goodfood.databinding.FragmentMealsBinding
import pw.prsk.goodfood.utils.ItemSwipeDecorator
import pw.prsk.goodfood.utils.MealItemTouchHelperCallback
import pw.prsk.goodfood.viewmodels.MealsViewModel

class MealsFragment : Fragment() {
    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(viewModel)
        viewModel.loadMealsList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
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
        val mealAdapter = MealAdapter()
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

        val ithCallback = MealItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvMealsList)
    }

    private fun subscribeUi(adapter: MealAdapter) {
        viewModel.mealList.observe(viewLifecycleOwner) { meals ->
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