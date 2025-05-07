package es.uam.eps.dadm.hearthstonecards.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.uam.eps.dadm.hearthstonecards.model.Collection

/**
 * Adapter for ViewPager2 that provides CollectionFragment instances for each collection
 *
 * @param fragmentActivity The activity where the ViewPager2 is hosted
 * @param colecciones List of Collection objects to be displayed
 * @param username The username of the current user
 */
class CollectionPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val colecciones: List<Collection>,
    private val username: String
) : FragmentStateAdapter(fragmentActivity) {

    /**
     * Returns the number of fragments
     *
     * @return The size of the collection list
     */
    override fun getItemCount(): Int = colecciones.size

    /**
     * Creates a new CollectionFragment for a given position
     *
     * @param position The index of the collection
     * @return A Fragment that shows the users cards
     */
    override fun createFragment(position: Int): Fragment {
        val collection = colecciones[position]
        // Send collection ID and username to fragment
        return CollectionFragment.newInstance(collection.id, username)
    }

}
