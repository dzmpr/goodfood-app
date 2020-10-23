package pw.prsk.goodfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment: Fragment() {
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
            when (it.itemId) {
                R.id.actionMeals -> {
                    Toast.makeText(context, "You prefer to dinner!", Toast.LENGTH_SHORT).show()
                }
                R.id.actionGroceries -> {
                    Toast.makeText(context, "You prefer go to store!", Toast.LENGTH_SHORT).show()
                }
                R.id.actionTemp -> {
                    Toast.makeText(context, "Temp pressed.", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        navigation.selectedItemId = R.id.actionMeals
    }
}