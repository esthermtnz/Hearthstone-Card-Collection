package es.uam.eps.dadm.hearthstonecards
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityLoginBinding
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val name = resources.getString(R.string.app_name)

        binding.btnLogin.setOnClickListener {

            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            //Print username and password
            Toast.makeText(this, "Username: $username, Password: $password", Toast.LENGTH_LONG).show()
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

        Timber.i("onCreate called")
    }
}

