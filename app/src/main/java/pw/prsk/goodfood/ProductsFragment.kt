package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip

import com.google.android.material.snackbar.Snackbar

import pw.prsk.goodfood.adapters.ProductAdapter
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.databinding.FragmentProductsBinding
import pw.prsk.goodfood.utils.ItemSwipeDecorator
import pw.prsk.goodfood.utils.ProductItemTouchHelperCallback
import pw.prsk.goodfood.viewmodels.ProductsViewModel

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(viewModel)
        viewModel.loadProductsList()
        viewModel.loadCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
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
        viewModel.categoriesList.observe(viewLifecycleOwner) {
            if (binding.cgCategoryChips.childCount > 0) {
                binding.cgCategoryChips.removeAllViews()
            }

            it.forEach { category ->
                val chip = layoutInflater.inflate(R.layout.chip_sort_layout, binding.cgCategoryChips, false) as Chip
                chip.text = category.name
                binding.cgCategoryChips.addView(chip)
            }
        }
    }

    private fun initProductList() {
        val productAdapter = ProductAdapter()
        subscribeUi(productAdapter)

        binding.rvProductsList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = productAdapter
        }

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder()
            .setRightSideIcon(R.drawable.ic_trash_bin, R.color.ivory)
            .setBackgroundColor(R.color.rose_madder)
            .setRightSideText(R.string.delete_action_label, R.color.ivory, 16f)
            .setIconMargin(50)
            .getDecorator()
        val ithCallback = ProductItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvProductsList)
    }

    private fun subscribeUi(adapter: ProductAdapter) {
        viewModel.productsList.observe(viewLifecycleOwner) { list ->
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