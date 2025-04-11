package es.uam.eps.dadm.hearthstonecards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.uam.eps.dadm.hearthstonecards.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    private var collectionId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionId = arguments?.getInt(ARG_COLLECTION_ID) ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val ARG_COLLECTION_ID = "collection_id"

        fun newInstance(id: Int): CollectionFragment {
            val fragment = CollectionFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARG_COLLECTION_ID, id)
            }
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
