package com.robertferreira.forbiddenlandscharcreator.ui.charcreation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.ui.charlist.CharListViewModel
import androidx.fragment.app.Fragment
import android.widget.*
import com.robertferreira.forbiddenlandscharcreator.*
import kotlinx.android.synthetic.main.fragment_charcreation.*

class CharCreationFragment : Fragment() {

    private lateinit var charCreationViewModel: CharCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charCreationViewModel =
            ViewModelProviders.of(this).get(CharCreationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charcreation, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        charCreationViewModel.text.observe(this, Observer {
            textView.text = it
        })*/


        val kinSpinner = root.findViewById<Spinner>(R.id.kinSpinner)
        val kinAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Kins.kins )
        kinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kinSpinner!!.setAdapter(kinAdapter)

        val professionSpinner = root.findViewById<Spinner>(R.id.professionSpinner)
        val professionAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Professions.professions )
        professionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        professionSpinner!!.setAdapter(professionAdapter)

        val ageSpinner = root.findViewById<Spinner>(R.id.ageSpinner)
        val ageAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Ages.ages )
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner!!.setAdapter(ageAdapter)

        setKinListener(kinSpinner)

        return root
    }

    fun setKinListener(kinSpinner : Spinner) {
       var listener = object: AdapterView.OnItemSelectedListener {
           override fun onNothingSelected(parent: AdapterView<*>?) {
                //clear Kin Talent Key
                //clear Prime Kin Attribute
           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               //alter Kin Talent Key to filter available kin talents and clear selected Kin Talent
               //alter Prime Kin Attribute based on selected Kin
           }
       }

        kinSpinner.onItemSelectedListener = listener
    }
}