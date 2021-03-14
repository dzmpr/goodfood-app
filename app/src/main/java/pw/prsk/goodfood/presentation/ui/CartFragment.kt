package pw.prsk.goodfood.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pw.prsk.goodfood.databinding.FragmentCartBinding
import pw.prsk.goodfood.presentation.adapter.CartAdapter
import pw.prsk.goodfood.presentation.viewmodel.CartViewModel
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

        viewModel.cartList.observe(viewLifecycleOwner) {
            cartAdapter.setList(it)
        }
    }
}