package es.uam.eps.dadm.hearthstonecards.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityCollectionsBinding
import es.uam.eps.dadm.hearthstonecards.view.CollectionPagerAdapter
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CollectionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionsBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collections)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val database = AppDatabase.getInstance(applicationContext)
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null)
        viewModel.setUsername(username)

        // Load collections from database
        lifecycleScope.launch {
            val colecciones = withContext(Dispatchers.IO) {
                database.collectionDao.getCollections()
            }

            // Adapter to see each collection as a tab
            val adapter = CollectionPagerAdapter(this@CollectionsActivity, colecciones, username!!)

            binding.viewPager.adapter = adapter

            //Tab for each collection
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = colecciones[position].name
            }.attach()
        }

        // Button to go back to main activity
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}