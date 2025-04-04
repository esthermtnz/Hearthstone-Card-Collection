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
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityMainBinding
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import timber.log.Timber

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

        val viewPager = binding.imageCarousel

        val images = viewModel.user.packs.map { it.picture }

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
                    // Acción para "Colección"
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

    /**
     * Function that handles when the user returns to this activity
     */
    override fun onResume() {
        super.onResume()
        val numPacks = viewModel.user.packs.size
        Toast.makeText(this, "¡Bienvenido! Tienes $numPacks sobres para abrir", Toast.LENGTH_SHORT).show()
    }
}

