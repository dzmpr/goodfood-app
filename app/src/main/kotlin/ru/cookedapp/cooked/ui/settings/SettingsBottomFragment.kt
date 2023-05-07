package ru.cookedapp.cooked.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.FragmentBottomSettingsBinding
import ru.cookedapp.cooked.ui.base.BaseBottomSheetFragment

class SettingsBottomFragment: BaseBottomSheetFragment() {
    private var _binding: FragmentBottomSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llItemManageCategories.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer)
                .navigate(R.id.actionNavigateToManageCategories)
            dismiss()
        }

        binding.llItemSettings.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).navigate(R.id.actionNavigateToSettings)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
