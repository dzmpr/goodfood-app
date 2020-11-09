package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import pw.prsk.goodfood.viewmodels.HomeViewModel
import java.lang.IllegalStateException

class HomeFragment: Fragment() {
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
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigation = view.findViewById<BottomNavigationView>(R.id.bnvBottomMenu)
        navigation.setOnNavigationItemSelectedListener {
            loadFragment(when (it.itemId) {
                R.id.actionMeals -> HomeTabs.MEALS
                R.id.actionGroceries -> HomeTabs.GROCERIES
                R.id.actionProfile -> HomeTabs.PROFILE
                else -> throw IllegalStateException("Unknown bottom bar action menu id.")
            })
            true
        }
        navigation.selectedItemId = R.id.actionMeals
    }

    private fun getFragment(tabs: HomeTabs): Fragment = when (tabs) {
        HomeTabs.MEALS -> MealsFragment()
        HomeTabs.GROCERIES -> GroceriesFragment()
        HomeTabs.PROFILE -> ProfileFragment()
    }

    private fun loadFragment(tab: HomeTabs) {
        childFragmentManager.commit {
            replace(R.id.flFragmentContainer, getFragment(tab))
        }
    }

    enum class HomeTabs {
        MEALS,
        GROCERIES,
        PROFILE
    }
}