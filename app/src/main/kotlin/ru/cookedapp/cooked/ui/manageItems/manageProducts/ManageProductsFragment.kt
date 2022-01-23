package ru.cookedapp.cooked.ui.manageItems.manageProducts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.ui.base.EditNameDialogFragment
import ru.cookedapp.cooked.ui.manageItems.BaseManageItemsFragment

class ManageProductsFragment : BaseManageItemsFragment() {

    override val viewModel: ManageProductsViewModel by viewModels { vmFactory }

    override val toolbarTextResId: Int = R.string.label_manage_products
    override val renameDialogTitleResId: Int = R.string.label_change_product_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            EditNameDialogFragment.RENAME_DIALOG_KEY,
            this
        ) { _, resultBundle ->
            val newName = resultBundle.getString(EditNameDialogFragment.RESULT_NAME_KEY)!!
            val itemId = resultBundle.getLong(EditNameDialogFragment.DATA_ID_KEY)
            viewModel.onProductRenamed(itemId, newName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productsList.observe(viewLifecycleOwner) { items ->
            setItems(items)
        }
    }
}
