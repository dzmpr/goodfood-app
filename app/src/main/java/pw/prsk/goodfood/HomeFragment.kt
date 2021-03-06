package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import pw.prsk.goodfood.databinding.FragmentHomeBinding
import pw.prsk.goodfood.viewmodels.HomeViewModel

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController : NavController

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val navFragment = childFragmentManager.findFragmentById(R.id.homeNavHost) as NavHostFragment
        navController = navFragment.navController
        binding.bnvBottomMenu.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Remove action on item reselected
        binding.bnvBottomMenu.setOnNavigationItemReselectedListener {}

        binding.bnvBottomMenu.selectedItemId = R.id.actionRecipes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}