package pw.prsk.goodfood.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import pw.prsk.goodfood.R
import pw.prsk.goodfood.databinding.FragmentCartBinding
import pw.prsk.goodfood.presentation.adapter.CartAdapter
import pw.prsk.goodfood.presentation.viewmodel.CartViewModel
import pw.prsk.goodfood.utils.ItemSwipeDecorator
import pw.prsk.goodfood.utils.RecipeListItemTouchHelperCallback
import javax.inject.Inject

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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
        val cartAdapter = CartAdapter(object : CartAdapter.BoughtChangeStateCallback {
            override fun changeBoughtState(id: Int, state: Boolean) {
                viewModel.changeBoughtState(id, state)
            }
        })

        binding.rvCartList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        val swipeDecorator = ItemSwipeDecorator.Companion.Builder()
            .setRightSideIcon(R.drawable.ic_remove_from_cart, R.color.ivory)
            .setBackgroundColor(R.color.rose_madder)
            .setRightSideText(R.string.label_remove, R.color.ivory, 16f)
            .setIconMargin(50)
            .getDecorator()
        val ithCallback = RecipeListItemTouchHelperCallback(viewModel, swipeDecorator)
        val touchHelper = ItemTouchHelper(ithCallback)
        touchHelper.attachToRecyclerView(binding.rvCartList)

        viewModel.cartList.observe(viewLifecycleOwner) {
            binding.tvCartPlaceholder.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            cartAdapter.setList(it)
        }
    }
}