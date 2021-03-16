package pw.prsk.goodfood.presentation.ui

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pw.prsk.goodfood.BuildConfig
import pw.prsk.goodfood.R
import pw.prsk.goodfood.databinding.FragmentBottomSettingsBinding

class SettingsBottomFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Open dialog with expanded state
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.llItemManageCategories.setOnClickListener {
            Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
        }

        binding.llItemManageProducts.setOnClickListener {
            Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
        }

        binding.llItemSettings.setOnClickListener {
            Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
        }

        binding.llItemBackup.setOnClickListener {
            Toast.makeText(context, "Not implemented", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}