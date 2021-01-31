package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import pw.prsk.goodfood.databinding.FragmentHomeBinding
import pw.prsk.goodfood.viewmodels.HomeViewModel

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.flFragmentContainer) == null) {
            loadFragment(HomeTabs.MEALS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bnvBottomMenu.setOnNavigationItemSelectedListener {
            loadFragment(when (it.itemId) {
                R.id.actionMeals -> HomeTabs.MEALS
                R.id.actionProducts -> HomeTabs.PRODUCTS
                R.id.actionProfile -> HomeTabs.PROFILE
                else -> throw IllegalStateException("Unknown bottom bar action menu id.")
            })
            true
        }
        // Remove action on item reselected
        binding.bnvBottomMenu.setOnNavigationItemReselectedListener {}

        binding.bnvBottomMenu.selectedItemId = R.id.actionMeals
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFragment(tabs: HomeTabs): Fragment = when (tabs) {
        HomeTabs.MEALS -> MealsFragment()
        HomeTabs.PRODUCTS -> ProductsFragment()
        HomeTabs.PROFILE -> ProfileFragment()
    }

    private fun loadFragment(tab: HomeTabs) {
        childFragmentManager.commit {
            replace(R.id.flFragmentContainer, getFragment(tab))
        }
    }

    enum class HomeTabs {
        MEALS,
        PRODUCTS,
        PROFILE
    }
}