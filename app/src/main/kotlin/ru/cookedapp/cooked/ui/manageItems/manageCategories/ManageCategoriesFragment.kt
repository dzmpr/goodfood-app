package ru.cookedapp.cooked.ui.manageItems.manageCategories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.ui.base.EditNameDialogFragment
import ru.cookedapp.cooked.ui.manageItems.BaseManageItemsFragment

class ManageCategoriesFragment : BaseManageItemsFragment() {

    override val viewModel: ManageCategoriesViewModel by viewModels { vmFactory }

    override val toolbarTextResId: Int = R.string.label_manage_categories
    override val renameDialogTitleResId: Int = R.string.label_change_category_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            EditNameDialogFragment.RENAME_DIALOG_KEY,
            this
        ) { _, resultBundle ->
            val newName = resultBundle.getString(EditNameDialogFragment.RESULT_NAME_KEY)!!
            val categoryId = resultBundle.getLong(EditNameDialogFragment.DATA_ID_KEY)
            viewModel.onCategoryRenamed(categoryId, newName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.categoryList.observe(viewLifecycleOwner) { items ->
            setItems(items)
        }
    }
}
