package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import pw.prsk.goodfood.adapters.ProductAdapter
import pw.prsk.goodfood.data.AppDatabase
import pw.prsk.goodfood.databinding.DialogAddProductBinding
import pw.prsk.goodfood.databinding.FragmentProductsBinding
import pw.prsk.goodfood.repository.ProductRepository
import pw.prsk.goodfood.viewmodels.ProductsViewModel

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbInstance = AppDatabase.getInstance(requireContext().applicationContext)
        val repository = ProductRepository(dbInstance)
        viewModel.injectRepository(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ProductAdapter()
        subscribeUi(productAdapter)

        binding.rvProductsList.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = productAdapter
        }

        binding.fabAddProduct.setOnClickListener {
            val bsd = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
            val dialogBinding = DialogAddProductBinding.inflate(layoutInflater)
            bsd.setContentView(dialogBinding.root)
            bsd.show()
        }
    }

    fun subscribeUi(adapter: ProductAdapter) {
        viewModel.productsList.observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}