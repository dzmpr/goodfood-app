package pw.prsk.goodfood.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import pw.prsk.goodfood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (savedInstanceState == null) {
//            val fragment = HomeFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.flContainer, fragment)
//                .commit()
//        }
    }

//    fun changeFragment() {
//        supportFragmentManager.commit {
//            setCustomAnimations(
//                R.anim.screen_slide_in,
//                R.anim.screen_fade_out,
//                R.anim.screen_fade_in,
//                R.anim.screen_slide_out
//            )
//            add(R.id.flContainer, EditMealFragment())
//            addToBackStack(null)
//        }
//    }
}