/**
 * Class that holds the functioning of the main activity view
 */
package es.uam.eps.dadm.hearthstonecards.view
import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityMainBinding
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import timber.log.Timber
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import kotlinx.coroutines.launch


/**
 * Definition of the MainActivity class
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val database = AppDatabase.getInstance(applicationContext)
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        viewModel.setUsername(username)
        viewModel.setUser(database.userDao.getUser(username!!))




        val adapter = ImageAdapter(viewModel.packs.value ?: listOf(), viewModel)
        binding.imageCarousel.adapter = adapter

        viewModel.packs.observe(this) { updatedPacks ->
            adapter.updatePacks(updatedPacks)
        }

       //Profile button popup
        binding.btnProfile?.setOnClickListener { view ->
            showPopupMenu(view)
        }


        Timber.i("onCreate called")
    }

    /**
     * Shows the popup menu on the main view and allows to interact with it
     */
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_profile, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_collection -> {
                    val intent = Intent(this, CollectionsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_friends -> {
                    // Acción para "Amigos"
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun loadPacksFromDB(){
        lifecycleScope.launch {
            val packs = AppDatabase.getInstance(applicationContext).packDao.getPacks()
            viewModel.setPacks(packs)
        }
    }

    /**
     * Function that handles when the user returns to this activity
     */
    override fun onResume() {
        super.onResume()
        val user = viewModel.getUser()

        val numPacks = user?.openTokens
        Toast.makeText(this, "¡Bienvenido! Tienes $numPacks sobres para abrir", Toast.LENGTH_SHORT).show()
    }
}

