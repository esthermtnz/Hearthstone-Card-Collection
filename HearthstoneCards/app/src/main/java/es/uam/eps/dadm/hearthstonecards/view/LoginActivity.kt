/**
 * Class created to make the login page functional
 */
package es.uam.eps.dadm.hearthstonecards.view
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
import kotlinx.coroutines.launch

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
}

