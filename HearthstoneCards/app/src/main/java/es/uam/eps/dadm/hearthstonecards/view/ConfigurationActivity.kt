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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityConfigurationBinding
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Definition of the ProfileActivity class
 */
class ConfigurationActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigurationBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_configuration)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val database = AppDatabase.getInstance(applicationContext)
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        viewModel.setUsername(username)
        lifecycleScope.launch {
            viewModel.setUser(database.userDao.getUser(username!!))
        }

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

}

