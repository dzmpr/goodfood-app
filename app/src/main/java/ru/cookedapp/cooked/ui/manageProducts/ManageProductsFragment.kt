package ru.cookedapp.cooked.ui.manageProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentManageProductsBinding
import ru.cookedapp.cooked.ui.base.EditNameDialogFragment
import ru.cookedapp.cooked.ui.CookedApp
import javax.inject.Inject

class ManageProductsFragment : Fragment() {
    private var _binding: FragmentManageProductsBinding? = null
    private val binding: FragmentManageProductsBinding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ManageProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as CookedApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(ManageProductsViewModel::class.java)

        childFragmentManager.setFragmentResultListener(
            EditNameDialogFragment.RENAME_DIALOG_KEY,
            this
        ) { _, resultBundle ->
            val itemId = resultBundle.getInt(EditNameDialogFragment.DATA_ID_KEY)
            val newName = resultBundle.getString(EditNameDialogFragment.RESULT_NAME_KEY)!!
            viewModel.onProductRename(itemId, newName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()

        binding.tbToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }
    }

    private fun initList() {
        val productsAdapter = ProductsAdapter(object: ProductsAdapter.ProductItemCallback {
            override fun onClick(id: Int, name: String) {
                val dialog = EditNameDialogFragment.getDialog(
                    getString(R.string.label_change_product_name),
                    name,
                    id
                )
                dialog.show(childFragmentManager, null)
            }
        })
        val manager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_EVENLY
        }

        binding.rvProductsList.apply {
            adapter = productsAdapter
            layoutManager = manager
        }

        viewModel.productsList.observe(viewLifecycleOwner) {
            productsAdapter.setList(it)
        }
    }
}
