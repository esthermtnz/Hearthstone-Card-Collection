package es.uam.eps.dadm.hearthstonecards.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.uam.eps.dadm.hearthstonecards.model.Collection

class CollectionPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val colecciones: List<Collection>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = colecciones.size

    override fun createFragment(position: Int): Fragment {
        val collection = colecciones[position]
        return CollectionFragment.newInstance(collection.id)
    }
}
