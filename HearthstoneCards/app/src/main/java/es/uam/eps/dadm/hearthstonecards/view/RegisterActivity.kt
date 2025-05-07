/**
 * Class created to make the register view functional
 */
package es.uam.eps.dadm.hearthstonecards.view

import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityRegisterBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.hearthstonecards.R
import timber.log.Timber
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.model.User
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch



/**
 * Manages the registration process for new users
 */
class RegisterActivity : AppCompatActivity()  {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        val database = AppDatabase.getInstance(applicationContext)
        val userDao = database.userDao

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        //Register button
        binding.btnRegister.setOnClickListener {
            //read user data
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val firstName = binding.firstName.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val phoneNumber = binding.telephoneNumber.text.toString().trim()

            //check data is not blank
            if (username.isBlank() || password.isBlank() || firstName.isBlank() ||
                lastName.isBlank() || email.isBlank() || phoneNumber.isBlank()){
                Toast.makeText(this, "All data must be filled before registering.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //if it isn't, create user before next checks
            val userToRegister = User(
                username=username, password = password, name = firstName, surname = lastName, email = email,
                tlf = phoneNumber, openTokens = 2
            )

            //check database for existing user. If not, add user to database and proceed. Else, deny the operation
            lifecycleScope.launch {
                val existingUser = userDao.getUser(username)
                if (existingUser == null){
                    userDao.addUser(userToRegister)
                    Timber.i("User registered successfully.")
                    runOnUiThread{
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        sharedPreferences.edit().putString("username", username).apply()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        Timber.i("Register done. Move to main page")
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Username already exists",Toast.LENGTH_SHORT).show()
                    Timber.i("Register not done. Username already exists.")
                }
            }

        }


        Timber.i("onCreate called")
    }
}
