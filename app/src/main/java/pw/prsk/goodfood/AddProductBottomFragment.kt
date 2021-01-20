package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.databinding.FragmentAddProductBinding
import pw.prsk.goodfood.viewmodels.ProductsViewModel

class AddProductBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddProductBinding

    // TODO: Mb refactor sharedViewModel
    private val viewModel: ProductsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bAddProduct.setOnClickListener {
            viewModel.addProduct(
                Product(
                    null,
                    binding.tilProductName.editText?.text.toString(),
                    0,
                    0
                )
            )
            dismiss()
        }
    }
}