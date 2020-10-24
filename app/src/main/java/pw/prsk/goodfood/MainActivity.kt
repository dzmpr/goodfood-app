package pw.prsk.goodfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var f = supportFragmentManager.findFragmentById(R.id.flContainer)
        if (f == null) {
            f = HomeFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, f)
                .commit()
        }
    }
}