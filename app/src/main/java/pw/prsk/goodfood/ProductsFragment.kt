package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager

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

        viewModel.deleteSnack.observe(viewLifecycleOwner) {
            val message = resources.getString(R.string.snackbar_item_deleted, it)
            showSnackbar(message)
        }

        binding.fabAddProduct.setOnClickListener {
            val dialog = AddProductBottomFragment()
            dialog.show(childFragmentManager, null)
        }

//        binding.fabAddProduct.setOnClickListener {
//            val bsd = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
//            val dialogBinding = FragmentAddProductBinding.inflate(layoutInflater)
//            dialogBinding.bAddProduct.setOnClickListener {
//                viewModel.addProduct(
//                    Product(null, dialogBinding.tilProductName.editText?.text.toString(), 0, 0)
//                )
//                bsd.dismiss()
//            }
//            bsd.setContentView(dialogBinding.root)
//            bsd.show()
//        }
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