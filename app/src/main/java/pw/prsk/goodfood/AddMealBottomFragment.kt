package pw.prsk.goodfood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.data.Meal
import pw.prsk.goodfood.databinding.FragmentAddMealBinding
import pw.prsk.goodfood.viewmodels.MealsViewModel
import java.time.LocalDateTime

class AddMealBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddMealBinding

    // TODO: Mb refactor sharedViewModel
    private val viewModel: MealsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMealBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bAddMeal.setOnClickListener {
            viewModel.addMeal(
                Meal(
                    null,
                    binding.tilMealName.editText?.text.toString(),
                    binding.tilDescription.editText?.text.toString(),
                    LocalDateTime.now(),
                    0,
                    0
                )
            )
            dismiss()
        }
    }
}