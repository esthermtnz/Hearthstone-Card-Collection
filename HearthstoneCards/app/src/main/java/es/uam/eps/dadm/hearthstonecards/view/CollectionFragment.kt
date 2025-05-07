package es.uam.eps.dadm.hearthstonecards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.databinding.FragmentCollectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment that displays the list of cards obtained by a user in a collection
 */
class CollectionFragment : Fragment() {
    // ViewBinding reference for the fragment layout
    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    // ID of the collection to be displayed
    private var collectionId: Int = 1

    // Username of the current user
    private lateinit var username: String

    /**
     * Initializes fragment arguments
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionId = arguments?.getInt(ARG_COLLECTION_ID) ?: 1
        username = arguments?.getString(ARG_USERNAME) ?: ""
    }

    /**
     * Inflates the layout for this fragment using ViewBinding
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Loads and displays the cards obtained by the user in the selected collection
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Load the obtained cards from the database
        lifecycleScope.launch {
            val database = AppDatabase.getInstance(requireContext())
            val cards = withContext(Dispatchers.IO) {
                database.obtainedCardCrossRefDao
                    .getObtainedCardsFromUsernameAndCollectionId(username, collectionId)
            }

            // Set the adapter to display cards
            val adapter = CardAdapter(cards)
            binding.recyclerView.adapter = adapter
        }
    }

    /**
     * Cleans up the binding reference when the view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_COLLECTION_ID = "collection_id"
        private const val ARG_USERNAME = "username"

        /**
         * Creates a new instance of CollectionFragment
         *
         * @param collectionId ID of the collection to display
         * @param username The username of the user
         * @return A CollectionFragment instance
         */
        fun newInstance(collectionId: Int, username: String): CollectionFragment {
            val fragment = CollectionFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARG_COLLECTION_ID, collectionId)
                putString(ARG_USERNAME, username)
            }
            return fragment
        }
    }
}
