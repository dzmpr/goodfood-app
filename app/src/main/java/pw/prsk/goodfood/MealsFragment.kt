package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import pw.prsk.goodfood.adapters.MealAdapter
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.data.Meal
import pw.prsk.goodfood.databinding.DialogAddMealBinding
import pw.prsk.goodfood.databinding.FragmentMealsBinding
import pw.prsk.goodfood.repository.MealRepository
import pw.prsk.goodfood.adapters.MealItemTouchHelperCallback
import pw.prsk.goodfood.viewmodels.MealsViewModel
import java.time.LocalDateTime

class MealsFragment : Fragment() {
    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val dbInstance = AppDatabase.getInstance(requireActivity().applicationContext)
        val mealRepository = MealRepository(dbInstance)
        viewModel.injectRepository(mealRepository)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealAdapter = MealAdapter()
        subscribeUi(mealAdapter)

        binding.rvMealsList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = mealAdapter
        }

        val ithCallback = MealItemTouchHelperCallback()
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvMealsList)

        binding.fabAddMeal.setOnClickListener {
            val bsd = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
            val dialogBinding = DialogAddMealBinding.inflate(layoutInflater)
            bsd.setContentView(dialogBinding.root)
            dialogBinding.bAddMeal.setOnClickListener {
                viewModel.addMeal(
                    Meal(
                        null,
                        dialogBinding.tilMealName.editText?.text.toString(),
                        dialogBinding.tilDescription.editText?.text.toString(),
                        LocalDateTime.now(),
                        0,
                        0
                    )
                )
                bsd.dismiss()
            }
            bsd.show()
        }
    }

    private fun subscribeUi(adapter: MealAdapter) {
        viewModel.mealList.observe(viewLifecycleOwner) { meals ->
            adapter.setList(meals)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}