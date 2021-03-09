package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import pw.prsk.goodfood.adapters.RecipeLineAdapter
import pw.prsk.goodfood.databinding.FragmentRecipeListBinding
import pw.prsk.goodfood.utils.ItemSwipeDecorator
import pw.prsk.goodfood.utils.ProductItemTouchHelperCallback
import pw.prsk.goodfood.viewmodels.RecipeListViewModel
import javax.inject.Inject

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(RecipeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProductList()
        initCategoryChips()

        viewModel.deleteSnack.observe(viewLifecycleOwner) {
            val message = resources.getString(R.string.snackbar_item_deleted, it)
            showSnackbar(message)
        }

        binding.fabAddProduct.setOnClickListener {
            val dialog = AddProductBottomFragment()
            dialog.show(childFragmentManager, null)
        }
    }

    private fun initCategoryChips() {
        binding.cgCategoryChips.setOnCheckedChangeListener { _, _ ->
            Toast.makeText(context, "Deprecated", Toast.LENGTH_SHORT).show()
        }

            if (binding.cgCategoryChips.childCount > 0) {
                binding.cgCategoryChips.removeAllViews()
            }

            for (i in 1 until 7) {
                val chip = layoutInflater.inflate(R.layout.chip_sort_layout, binding.cgCategoryChips, false) as Chip
                chip.text = "Chip $i"
                chip.id = i
                binding.cgCategoryChips.addView(chip)
            }
    }

    private fun initProductList() {
        val recipeLineAdapter = RecipeLineAdapter()
        subscribeUi(recipeLineAdapter)

        binding.rvRecipesList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = recipeLineAdapter
        }

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder()
            .setRightSideIcon(R.drawable.ic_trash_bin, R.color.ivory)
            .setBackgroundColor(R.color.rose_madder)
            .setRightSideText(R.string.delete_action_label, R.color.ivory, 16f)
            .setIconMargin(50)
            .getDecorator()
        val ithCallback = ProductItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvRecipesList)
    }

    private fun subscribeUi(adapter: RecipeLineAdapter) {
        viewModel.recipeList.observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
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