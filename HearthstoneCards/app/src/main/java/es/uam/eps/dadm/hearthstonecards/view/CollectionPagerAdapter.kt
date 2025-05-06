package es.uam.eps.dadm.hearthstonecards.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.uam.eps.dadm.hearthstonecards.model.Collection

class CollectionPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val colecciones: List<Collection>,
    private val username: String
) : FragmentStateAdapter(fragmentActivity) {

    // Returns the number of fragments
    override fun getItemCount(): Int = colecciones.size

    // Creates a new fragment for the given position
    override fun createFragment(position: Int): Fragment {
        val collection = colecciones[position]
        // Send collection ID and username to fragment
        return CollectionFragment.newInstance(collection.id, username)
    }

}
