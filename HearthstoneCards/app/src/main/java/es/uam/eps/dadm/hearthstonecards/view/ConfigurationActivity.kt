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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        val firestore = FirebaseFirestore.getInstance()
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        viewModel.setUsername(username)
        binding.btnUploadFirebase.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    val _username = database.userDao.getUser(username!!)
                    if (_username != null){
                        withContext(Dispatchers.Main){
                            viewModel.setUser(_username)
                        }
                    }

                    val user = viewModel.getUser() ?: return@withContext
                    val obtainedCards = database.obtainedCardCrossRefDao.getObtainedCardsFromUsername(viewModel.getUsername()!!)

                    val backupData = hashMapOf(
                        "user_data" to user,
                        "obtained_cards" to obtainedCards
                    )

                    firestore.collection("users").document(username)
                        .set(backupData).addOnSuccessListener {
                            Toast.makeText(applicationContext, "Backup subido correctamente", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "Error al subir el backup", Toast.LENGTH_SHORT).show()
                            Timber.e(it)
                        }
                }

            }
        }

        binding.btnDownloadFirebase.setOnClickListener {
            firestore.collection("users").document(username!!)
                .get()
                .addOnSuccessListener { document->
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){

                            if(document != null && document.exists()) {
                                val userData = document.get("user_data") as? Map<String, Any>
                                val obtainedCards =
                                    document.get("obtained_cards") as? List<Map<String, Any>>

                                if (userData != null) {
                                    val user = viewModel.getUser()
                                    if (user != null) {
                                        user.openTokens = (userData["openTokens"] as Long).toInt()
                                        database.userDao.updateUser(user)
                                        withContext(Dispatchers.Main){
                                            viewModel.setUser(user)
                                        }
                                    }
                                }

                                obtainedCards?.forEach {
                                    val packId = (it["collectionId"] as Long).toInt()
                                    val cardId = (it["cardId"] as Long).toInt()
                                    val quantity = (it["quantity"] as Long).toInt()
                                    database.obtainedCardCrossRefDao.updateQuantity(
                                        username,
                                        packId,
                                        cardId,
                                        quantity
                                    )
                                }
                                withContext(Dispatchers.Main){
                                    Toast.makeText(
                                        applicationContext,
                                        "Backup restaurado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            else{
                                withContext(Dispatchers.Main){
                                    Toast.makeText(applicationContext, "No se ha encontrado el backup", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "Error al descargar Backup", Toast.LENGTH_SHORT).show()
                    //Timber.e(it)
                }
        }

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }


}