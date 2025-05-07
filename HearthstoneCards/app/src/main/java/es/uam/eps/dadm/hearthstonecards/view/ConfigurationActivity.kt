/**
 * Class that allows the user to check their profile
 */
package es.uam.eps.dadm.hearthstonecards.view
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityConfigurationBinding
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import com.google.firebase.firestore.FirebaseFirestore
import es.uam.eps.dadm.hearthstonecards.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Definition of the ProfileActivity class
 */
class ConfigurationActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigurationBinding
    lateinit var apiService: ApiService

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

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        apiService = retrofit.create(ApiService::class.java)

        viewModel.setUsername(username)

        binding.btnGET?.setOnClickListener {
            getCommentFromApi()
        }
        binding.btnPOST?.setOnClickListener {
            postCommentToApi()
        }

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
            try{
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
                                        val user = User(userData.get("name").toString(),
                                            userData.get("surname").toString(),
                                            userData.get("email").toString(),
                                            userData.get("tlf").toString(),
                                            userData.get("password").toString(),
                                            userData.get("username").toString(),
                                            (userData.get("openTokens") as Long).toInt(),
                                            userData.get("icon").toString()
                                            )

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
            catch(e: Exception){
                Timber.e("Error: ${e.message}")
            }
        }

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }


    private fun getCommentFromApi(){
        lifecycleScope.launch {
            try {
                val response = apiService.getComment(1)
                if(response.isSuccessful){
                    val comment = response.body()
                    Toast.makeText(applicationContext, "Comment: $comment", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postCommentToApi(){
        val newComment = Comment(
            postId = 1,
            name = "test",
            email = "test@gmail.com",
            body = "test body",
            id = 1
        )
        lifecycleScope.launch{
            val response = apiService.postComment(newComment)
            try {
                if (response.isSuccessful) {
                    val postedComment = response.body()
                    Toast.makeText(applicationContext, "POSTed: $postedComment", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            catch(e: Exception){
                Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}