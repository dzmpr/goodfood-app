package pw.prsk.goodfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pw.prsk.goodfood.databinding.ActivityMainBinding
import pw.prsk.goodfood.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var f = supportFragmentManager.findFragmentById(R.id.flContainer)
        if (f == null) {
            f = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, f)
                .commit()
        }
    }
}