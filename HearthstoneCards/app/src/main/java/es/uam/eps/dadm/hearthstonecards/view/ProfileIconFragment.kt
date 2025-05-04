package es.uam.eps.dadm.hearthstonecards.view

import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.FragmentProfileIconBinding

class ProfileIconFragment : DialogFragment() {
    private var _binding: FragmentProfileIconBinding? = null
    private val binding get() = _binding!!

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onIconSelected(iconName: String){
        val activity = requireActivity() as ProfileActivity
        activity.onIconSelected(iconName)
        dismiss()
    }
}