package ru.cookedapp.cooked.ui.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.databinding.FragmentRecipeListBinding
import ru.cookedapp.cooked.extensions.setViewVisibility
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.recipeDetails.RecipeDetailsFragment
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.cooked.ui.recipeList.viewHolders.RecipeViewHolder
import ru.cookedapp.cooked.utils.ItemSwipeDecorator
import ru.cookedapp.cooked.utils.RecipeListItemTouchHelperCallback
import ru.cookedapp.cooked.utils.listBase.ListAdapter
import ru.cookedapp.cooked.utils.listBase.ViewHolderFactoryProvider
import ru.cookedapp.cooked.utils.listBase.data.ItemEvent

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(RecipeListViewModel::class.java)

        handleArguments()
    }

    private fun handleArguments() {
        val sourceKey = arguments?.getInt(LIST_TYPE_KEY)
        if (sourceKey != null) {
            viewModel.setListSource(sourceKey)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initCategoryChips()

        viewModel.deleteSnack.observe(viewLifecycleOwner) {
            val message = resources.getString(R.string.snackbar_item_deleted, it)
            showSnackbar(message)
        }
    }

    private fun initCategoryChips() {
        binding.cgCategoryChips.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                View.NO_ID -> {
                    viewModel.onCategorySelected(0)
                }
                else -> {
                    val category = group[checkedId].tag as RecipeCategoryEntity
                    viewModel.onCategorySelected(category.id!!)
                }
            }
        }

        viewModel.categorySet.observe(viewLifecycleOwner) { set ->
            if (set.size > 1) {
                binding.cgCategoryChips.setViewVisibility(true)

                if (binding.cgCategoryChips.childCount > 0) {
                    binding.cgCategoryChips.removeAllViews()
                    viewModel.onCategorySelected(0)
                }

                for ((index, category) in set.withIndex()) {
                    val chip = layoutInflater.inflate(
                        R.layout.chip_sort_layout,
                        binding.cgCategoryChips,
                        false
                    ) as Chip
                    chip.text = category.name
                    chip.id = index
                    chip.tag = category
                    binding.cgCategoryChips.addView(chip)
                }
            } else {
                binding.cgCategoryChips.setViewVisibility(false)
            }
        }
    }

    private fun initList() {
        val holderFactoryProvider = ViewHolderFactoryProvider(
            RecipeListItem::class to RecipeViewHolder.getFactory(),
        )
        val recipeListAdapter = ListAdapter(lifecycleScope, holderFactoryProvider) { event ->
            when (event) {
                is ItemEvent.Checked -> when (event.item) {
                    is RecipeListItem -> {
                        viewModel.changeFavoriteState(event.item.id.toInt(), event.newCheckedState)
                    }
                }
                is ItemEvent.Click -> when (event.item) {
                    is RecipeListItem -> {
                        val args = RecipeDetailsFragment.createOpenRecipeBundle(event.item.id.toInt())
                        Navigation.findNavController(requireActivity(), R.id.fcvContainer)
                            .navigate(R.id.actionNavigateToRecipe, args)
                    }
                }
                is ItemEvent.Custom,
                is ItemEvent.Delete -> error("Unexpected event $event.")
            }
        }

        binding.rvRecipesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        subscribeUi(recipeListAdapter)

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder().run {
            setRightSideIcon(R.drawable.ic_trash_bin, R.color.ivory)
            setBackgroundColor(R.color.rose_madder)
            setRightSideText(R.string.delete_action_label, R.color.ivory, 16f)
            setIconMargin(50)
            getDecorator()
        }
        val ithCallback = RecipeListItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvRecipesList)
    }

    private fun subscribeUi(adapter: ListAdapter) {
        viewModel.recipeList.observe(viewLifecycleOwner) { list ->
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

    companion object {
        const val LIST_TYPE_KEY = "list_type_key"

        const val LIST_TYPE_ALL = 0
        const val LIST_TYPE_FAVORITES = 1
        const val LIST_TYPE_FREQUENT = 2
    }
}
