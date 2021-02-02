package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import pw.prsk.goodfood.databinding.FragmentEditMealBinding

class EditMealFragment : Fragment() {
    private lateinit var binding: FragmentEditMealBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMealBinding.inflate(inflater, container, false)
        return binding.root
    }
}