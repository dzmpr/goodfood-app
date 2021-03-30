package pw.prsk.goodfood.presentation.ui

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
import pw.prsk.goodfood.R
import pw.prsk.goodfood.databinding.FragmentManageCategoriesBinding
import pw.prsk.goodfood.presentation.adapter.CategoryAdapter
import pw.prsk.goodfood.presentation.viewmodel.ManageCategoriesViewModel
import javax.inject.Inject

class ManageCategoriesFragment : Fragment() {
    private var _binding: FragmentManageCategoriesBinding? = null
    private val binding: FragmentManageCategoriesBinding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ManageCategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(ManageCategoriesViewModel::class.java)

        childFragmentManager.setFragmentResultListener(
            EditNameDialogFragment.RENAME_DIALOG_KEY,
            this
        ) { _, resultBundle ->
            val newName = resultBundle.getString(EditNameDialogFragment.RESULT_NAME_KEY)!!
            val categoryId = resultBundle.getInt(EditNameDialogFragment.DATA_ID_KEY)
            viewModel.onCategoryRename(categoryId, newName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }

        initList()
    }

    private fun initList() {
        val listAdapter = CategoryAdapter(object : CategoryAdapter.CategoryItemCallback {
            override fun onClick(id: Int, name: String) {
                val dialog = EditNameDialogFragment.getDialog(
                    resources.getString(R.string.label_change_category_name),
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

        binding.rvCategoriesList.apply {
            adapter = listAdapter
            layoutManager = manager
        }

        viewModel.categoryList.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
        }
    }
}