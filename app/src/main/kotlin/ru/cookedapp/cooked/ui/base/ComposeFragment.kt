package ru.cookedapp.cooked.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.cookedapp.cooked.ui.theme.CookedTheme
import ru.cookedapp.storage.appSettings.AppSettings
import javax.inject.Inject

internal abstract class ComposeFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appSettings: AppSettings

    protected val navController: NavController
        get() {
            val navHostFragment = parentFragment as? NavHostFragment
                ?: error("Parent should be NavHostFragment.")
            return navHostFragment.findNavController()
        }

    @Composable
    abstract fun Content()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val isDynamicThemeEnabled by appSettings.dynamicThemeState.collectAsStateWithLifecycle(
                initialValue = false,
            )

            CookedTheme(isDynamicThemeEnabled = isDynamicThemeEnabled) {
                this@ComposeFragment.Content()
            }
        }
    }

    protected inline fun <reified VM : ViewModel> viewModel(): Lazy<VM> = viewModels { vmFactory }
}
