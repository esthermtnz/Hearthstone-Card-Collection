/**
 * Class created to make the login page functional
 */
package es.uam.eps.dadm.hearthstonecards.view
import android.content.Context
import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityLoginBinding
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.hearthstonecards.R
import timber.log.Timber
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private lateinit var auth: FirebaseAuth
private lateinit var googleSignInClient: GoogleSignInClient
private val RC_SIGN_IN = 9001

/**
 * Definition of the LoginActivity class
 */
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val database = AppDatabase.getInstance(applicationContext)
        val userDao = database.userDao

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //Create Account Button
        binding.txtRegister.setOnClickListener {
            //Move to Register
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

            Timber.i("Move to register page")
        }

        //Login button
        binding.btnLogin.setOnClickListener {

            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            lifecycleScope.launch {
                val user = userDao.getUser(username)

                if(user != null && user.password == password){
                    //Save username for other activities that would make use of it
                    val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    sharedPreferences.edit().putString("username", user.username).apply()
                    Timber.i("Login correcto, $username $password")
                    Toast.makeText(this@LoginActivity, "Bienvenido, $username", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Timber.i("Login incorrecto.")
                    Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.btnGoogleSignIn?.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        //If 'X' pressed, delete form content from the username field
        binding.username.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0)
        binding.username.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = binding.username.compoundDrawables[2]
                if (drawableRight != null && event.x >= binding.username.width - binding.username.paddingRight - drawableRight.bounds.width()) {
                    binding.username.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }
        //If 'X' pressed, delete form content from the password field
        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0)
        binding.password.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = binding.password.compoundDrawables[2]
                if (drawableRight != null && event.x >= binding.password.width - binding.password.paddingRight - drawableRight.bounds.width()) {
                    binding.password.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
                Timber.e(e, "Google sign-in failed:${e.statusCode}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val username = user?.displayName ?: "GoogleUser"
                    val email = user?.email ?: "sinemail@firebase"
                    val password = "google_auth"

                    // Save in shared preferences
                    val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    sharedPreferences.edit().putString("username", username).apply()

                    // Save in the Room database
                    lifecycleScope.launch {
                        val userDao = AppDatabase.getInstance(applicationContext).userDao
                        val existingUser = userDao.getUser(username)

                        if (existingUser == null) {
                            val nuevoUsuario = es.uam.eps.dadm.hearthstonecards.model.User(
                                username = username,
                                email = email,
                                password = password,
                                name = username,
                                surname = "",
                                tlf = "N/A",
                                openTokens = 2,
                            )
                            userDao.addUser(nuevoUsuario)
                            Timber.i("Usuario Google insertado en la base de datos local: $username")
                        } else {
                            Timber.i("Usuario Google ya existente en la base de datos: $username")
                        }

                        // Go to MainActivity
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Bienvenido, $username", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

