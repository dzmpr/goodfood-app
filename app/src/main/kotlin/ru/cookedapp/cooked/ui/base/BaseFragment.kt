package ru.cookedapp.cooked.ui.base

import androidx.fragment.app.Fragment
import javax.inject.Inject
import ru.cookedapp.common.resourceProvider.ResourceProvider

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var rp: ResourceProvider
}
