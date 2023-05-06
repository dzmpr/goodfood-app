package ru.cookedapp.cooked.ui.manageItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import ru.cookedapp.common.baseList.ListAdapter
import ru.cookedapp.common.baseList.ViewHolderFactoryProvider
import ru.cookedapp.common.baseList.data.Item
import ru.cookedapp.common.baseList.data.ItemEvent
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentManageItemsBinding
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.base.BaseFragment
import ru.cookedapp.cooked.ui.base.EditNameDialogFragment
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemModel
import ru.cookedapp.cooked.ui.manageItems.viewHolders.ManageItemHolder

abstract class BaseManageItemsFragment : BaseFragment<FragmentManageItemsBinding>() {

    private lateinit var itemsAdapter: ListAdapter

    abstract val toolbarTextResId: Int
    abstract val renameDialogTitleResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentManageItemsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.tbToolbar) {
            title = rp.getString(toolbarTextResId)
            setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigateUp()
            }
        }

        initList()
    }

    protected fun setItems(items: List<Item>) {
        itemsAdapter.setList(items)
    }

    private fun initList() {
        val holderFactoryProvider = ViewHolderFactoryProvider(
            ManageItemModel::class to ManageItemHolder.getFactory(),
        )
        itemsAdapter = ListAdapter(lifecycleScope, holderFactoryProvider) { event ->
            when (event) {
                is ItemEvent.Click -> when (event.item) {
                    is ManageItemModel -> showDialogForItem(event.item as ManageItemModel)
                }
                is ItemEvent.Checked,
                is ItemEvent.Delete,
                is ItemEvent.Custom -> error("Unexpected event: $event.")
            }
        }

        val manager = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_EVENLY
        }

        binding.rvItemsList.apply {
            adapter = itemsAdapter
            layoutManager = manager
        }
    }

    private fun showDialogForItem(item: ManageItemModel) {
        val dialog = EditNameDialogFragment.getDialog(
            rp.getString(renameDialogTitleResId),
            item.name,
            item.id,
        )
        dialog.show(childFragmentManager, null)
    }
}
