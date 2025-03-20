package es.uam.eps.dadm.hearthstonecards.view

import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityRegisterBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import es.uam.eps.dadm.hearthstonecards.R
import timber.log.Timber

class RegisterActivity : AppCompatActivity()  {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        //Register button
        binding.btnRegister.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Timber.i("Register done. Move to main page")
        }


        Timber.i("onCreate called")
    }
}
