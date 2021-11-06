package ru.cookedapp.cooked.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentCartBinding
import ru.cookedapp.cooked.extensions.setViewVisibility
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.cart.data.CartViewType
import ru.cookedapp.cooked.utils.ItemSwipeDecorator
import ru.cookedapp.cooked.utils.RecipeListItemTouchHelperCallback
import ru.cookedapp.cooked.utils.listBase.data.ItemEvent

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory).get(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()

        binding.tbToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionRemovePurchased -> {
                    viewModel.onRemovePurchasedClick()
                    true
                }
                R.id.actionClearCart -> {
                    viewModel.onClearCartClick()
                    true
                }
                else -> false
            }
        }
    }

    private fun initList() {
        val cartAdapter = CartAdapter(lifecycleScope) { event ->
            when (event) {
                is ItemEvent.Checked -> when (event.item.type) {
                    CartViewType.ITEM -> {
                        viewModel.changeBoughtState(event.item.id.toInt(), event.newCheckedState)
                    }
                }
                is ItemEvent.Click,
                is ItemEvent.Custom,
                is ItemEvent.Delete -> error("No action.")
            }
        }

        binding.rvCartList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder().run {
            setRightSideIcon(R.drawable.ic_remove_from_cart, R.color.ivory)
            setBackgroundColor(R.color.rose_madder)
            setRightSideText(R.string.label_remove, R.color.ivory, 16f)
            setIconMargin(50)
            getDecorator()
        }
        val ithCallback = RecipeListItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvCartList)

        viewModel.cartList.observe(viewLifecycleOwner) {
            binding.tvCartPlaceholder.setViewVisibility(it.isEmpty())
            cartAdapter.setList(it)
        }
    }
}
