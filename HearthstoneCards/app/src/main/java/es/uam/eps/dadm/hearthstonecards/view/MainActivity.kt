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
import es.uam.eps.dadm.hearthstonecards.model.ObtainedCardCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

private lateinit var googleSignInClient: GoogleSignInClient



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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        val database = AppDatabase.getInstance(applicationContext)
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        viewModel.setUsername(username)
        lifecycleScope.launch {
            viewModel.setUser(database.userDao.getUser(username!!))
            val packs = database.packDao.getPacks()
            viewModel.setPacks(packs)
            val adapter = ImageAdapter(viewModel.getPacks(), viewModel){ packId ->
                lifecycleScope.launch{
                    openPack(packId)
                }
            }
            binding.imageCarousel.adapter = adapter
        }







        /*viewModel.packs.observe(this) { updatedPacks ->
            adapter.updatePacks(updatedPacks)
        }*/

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
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.action_collection -> {
                    startActivity(Intent(this, CollectionsActivity::class.java))
                    true
                }
                R.id.action_logout -> {
                    val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    sharedPrefs.edit().remove("username").apply()

                    val auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser
                    val isGoogleUser = currentUser?.providerData?.any { it.providerId == "google.com" } == true

                    if (isGoogleUser) {
                        googleSignInClient.signOut().addOnCompleteListener {
                            auth.signOut()
                            redirectToLogin()
                        }
                    } else {
                        auth.signOut()
                        redirectToLogin()
                    }
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    /*private fun loadPacksFromDB(){
        val packs = AppDatabase.getInstance(applicationContext).packDao.getPacks()
        viewModel.setPacks(packs)
    }*/

    suspend fun openPack(packId: Int){
        lifecycleScope.launch{
            withContext(Dispatchers.IO){
            val database = AppDatabase.getInstance(applicationContext)
            val user = viewModel.getUser() ?: return@withContext

            if(user.openTokens <= 0){
                Toast.makeText(this@MainActivity, "No tienes más sobres disponibles en este momento", Toast.LENGTH_SHORT).show()
                return@withContext
            }
                val cards = database.collectionCardCrossRefDao.getCardsFromCollectionId(packId)?.shuffled()?.take(5)
                val cardIds = cards?.map {it.cardId}
                for (card in cardIds!!){
                    val actualQuantity = database.obtainedCardCrossRefDao.getQuantity(user.username, packId, card)
                    if (actualQuantity == null){
                        database.obtainedCardCrossRefDao.addObtainedCard(
                            ObtainedCardCrossRef(user.username, packId, card, 1)
                        )
                    }
                    else{
                        database.obtainedCardCrossRefDao.updateQuantity(
                            user.username, packId, card, actualQuantity+1
                        )
                    }
                }

                database.userDao.decreaseToken(user.username)
                withContext(Dispatchers.Main) {
                    user.openTokens -= 1
                    viewModel.setUser(user)
                }
            }
        }
    }

    /**
     * Function that handles when the user returns to this activity
     */
    override fun onResume() {
        super.onResume()

        viewModel.openTokens.observe(this){ tokens ->
            Toast.makeText(this, "¡Bienvenido! Tienes $tokens sobres para abrir", Toast.LENGTH_SHORT).show()

        }


        /*val numPacks = viewModel.openTokens.value
        Toast.makeText(this, "¡Bienvenido! Tienes $numPacks sobres para abrir", Toast.LENGTH_SHORT).show()
        */
    }

}

