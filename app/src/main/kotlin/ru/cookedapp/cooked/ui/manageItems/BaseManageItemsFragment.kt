package ru.cookedapp.cooked.ui.manageItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import javax.inject.Inject
import ru.cookedapp.common.base.list.ListAdapter
import ru.cookedapp.common.base.list.ViewHolderFactoryProvider
import ru.cookedapp.common.base.list.data.Item
import ru.cookedapp.common.base.list.data.ItemEvent
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentManageItemsBinding
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.base.EditNameDialogFragment
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemModel
import ru.cookedapp.cooked.ui.manageItems.viewHolders.ManageItemHolder

abstract class BaseManageItemsFragment : Fragment() {

    private var _binding: FragmentManageItemsBinding? = null
    private val binding: FragmentManageItemsBinding get() = _binding!!

    private lateinit var itemsAdapter: ListAdapter

    abstract val toolbarTextResId: Int
    abstract val renameDialogTitleResId: Int

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.tbToolbar) {
            title = resources.getString(toolbarTextResId)
            setNavigationOnClickListener {
                Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
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
                is ItemEvent.Custom -> error("No action.")
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
            resources.getString(renameDialogTitleResId),
            item.name,
            item.id,
        )
        dialog.show(childFragmentManager, null)
    }
}
