package ru.cookedapp.cooked.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject
import ru.cookedapp.common.resourceProvider.ResourceProvider

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected open val toolbar: Toolbar? = null

    protected abstract val viewModel: BaseViewModel

    @Inject
    lateinit var rp: ResourceProvider

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    abstract fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarIfProvided()
    }

    private fun setupToolbarIfProvided() {
        val toolbar = toolbar ?: return
        setupToolbar(toolbar, viewModel.toolbarConfig)
    }

    private fun setupToolbar(toolbar: Toolbar, config: ToolbarConfig) {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        with(activity.supportActionBar) {
            this?.title = config.title
            this?.subtitle = config.subtitle
            this?.setHomeButtonEnabled(config.backButtonEnabled)
            this?.setDisplayHomeAsUpEnabled(config.backButtonEnabled)
        }
        if (config.backButtonEnabled && config.backButtonResId != null) {
            toolbar.setNavigationIcon(config.backButtonResId)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
