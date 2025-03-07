package es.uam.eps.dadm.hearthstonecards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = resources.getString(R.string.app_name)

        Toast.makeText(this, name, Toast.LENGTH_LONG).show()
    }
}