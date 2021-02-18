package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pw.prsk.goodfood.adapters.IngredientAdapter
import pw.prsk.goodfood.databinding.FragmentEditMealBinding
import pw.prsk.goodfood.viewmodels.EditMealViewModel

class EditMealFragment : Fragment() {
    private lateinit var binding: FragmentEditMealBinding

    private val viewModel: EditMealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(viewModel)
        viewModel.loadProducts()
        viewModel.loadUnits()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listAdapter = IngredientAdapter()

        binding.rvIngredientsList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(this.context,
                LinearLayoutManager.VERTICAL)
            )
        }

        viewModel.ingredients.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
        }

        binding.bAddIngredient.setOnClickListener {
            val sheet = AddIngredientBottomFragment()
            sheet.show(childFragmentManager, null)
        }
    }
}