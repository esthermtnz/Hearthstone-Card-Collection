package es.uam.eps.dadm.hearthstonecards.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.ActivityCollectionsBinding
import es.uam.eps.dadm.hearthstonecards.view.CollectionPagerAdapter
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel


class CollectionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collections)
        val viewModel = MainViewModel()
        val colecciones = viewModel.user.collections

        //Back button
        binding.btnBack.setOnClickListener {
            //Move to Main
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        val adapter = CollectionPagerAdapter(this, colecciones)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = colecciones[position].name
        }.attach()

    }
}