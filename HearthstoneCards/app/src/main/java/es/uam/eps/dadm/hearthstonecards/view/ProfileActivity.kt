/**
 * Class that allows the user to check their profile
 */
package es.uam.eps.dadm.hearthstonecards.view
import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityProfileBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Activity that allows the user to view their profile information and setup the icon
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

        binding.btnProfile.setOnClickListener {
            showFragment()
        }

    }

    /**
     * Callback called when a new icon is selected from the icon fragment
     *
     * @param iconName The name of the selected drawable resource
     */
    fun onIconSelected(iconName: String) {
        viewModel.updateUserIcon(this, iconName)
        Toast.makeText(this, "Icon selected: $iconName", Toast.LENGTH_SHORT).show()
    }

    /**
     * Displays the fragment that lets the user choose a profile icon
     */
    private fun showFragment() {
        val  fragment = ProfileIconFragment()
        fragment.show(supportFragmentManager, "profile_icon")

    }
}

