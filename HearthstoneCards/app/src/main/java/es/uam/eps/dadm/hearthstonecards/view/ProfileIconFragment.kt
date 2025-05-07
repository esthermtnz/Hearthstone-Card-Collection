package es.uam.eps.dadm.hearthstonecards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import es.uam.eps.dadm.hearthstonecards.databinding.FragmentProfileIconBinding

/**
 * Fragment that allows the user to select a profile icon
 */
class ProfileIconFragment : DialogFragment() {
    private var _binding: FragmentProfileIconBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the fragment layout and sets up the listeners
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileIconBinding.inflate(inflater, container, false)

        binding.icon1.setOnClickListener{
            onIconSelected("priest_icon")
        }
        binding.icon2.setOnClickListener {
            onIconSelected("warrior_icon")
        }

        return binding.root
    }

    /**
     * Clears the binding reference when the view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Sends the selected icon name back to the ProfileActivity
     *
     * @param iconName The name of the selected drawable resource
     */
    private fun onIconSelected(iconName: String){
        val activity = requireActivity() as ProfileActivity
        activity.onIconSelected(iconName)
        dismiss()
    }
}