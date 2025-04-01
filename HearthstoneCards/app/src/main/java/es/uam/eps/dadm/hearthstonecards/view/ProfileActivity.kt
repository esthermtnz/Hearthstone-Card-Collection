/**
 * Class that allows the user to check their profile
 */
package es.uam.eps.dadm.hearthstonecards.view
import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityProfileBinding
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import timber.log.Timber

/**
 * Definition of the ProfileActivity class
 */
class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}

