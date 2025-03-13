package es.uam.eps.dadm.hearthstonecards

import android.content.Intent
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityRegisterBinding
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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


        Timber.i("onCreate called")
    }
}
