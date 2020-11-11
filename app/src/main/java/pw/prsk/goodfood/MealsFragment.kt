package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.adapters.MealAdapter
import pw.prsk.goodfood.viewmodels.MealsViewModel

class MealsFragment : Fragment() {
    private val viewModel: MealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_meals, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvMeals: RecyclerView = view.findViewById(R.id.rvMealsList)
        rvMeals.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = MealAdapter()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}