package edu.msudenver.cs3013.project03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var settingText: TextView
    private lateinit var preference1: TextView
    private lateinit var preference2: TextView
    private lateinit var preference3: TextView
    private lateinit var preference4: TextView
    private lateinit var preference5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val username = arguments?.getString("username")


        val settingMessage = getString(R.string.settings_fragment, username)
        settingText = view.findViewById(R.id.setting_text)
        settingText.text = settingMessage
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference1 = view.findViewById(R.id.preference_header)
        preference2 = view.findViewById(R.id.preference_header2)
        preference3 = view.findViewById(R.id.preference_header3)
        preference4 = view.findViewById(R.id.preference_header4)
        preference5 = view.findViewById(R.id.preference_header5)

        preference3.text = "Username: " //+ userData.username.toString()
        preference1.text = "First Name: " //+ userData.firstName.toString()
        preference2.text = "Last Name: "// + userData.lastName.toString()
        preference4.text = "Age: " //+ userData.age.toString()
        preference5.text = "Favorite Instrument: "// + userData.favoriteInstrument.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}