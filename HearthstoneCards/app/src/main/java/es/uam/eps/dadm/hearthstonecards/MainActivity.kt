package es.uam.eps.dadm.hearthstonecards
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.image_carousel)

        // Lista de imágenes (solo 'sobre.png' por ahora)
        val images = listOf(R.drawable.monster_pack, R.drawable.character_pack)

        // Asignar adaptador al ViewPager2
        viewPager.adapter = ImageAdapter(images)

        Timber.i("onCreate called")
    }
}

