package com.robertferreira.forbiddenlandscharcreator.ui.charcreation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.ui.charlist.CharListViewModel
import androidx.fragment.app.Fragment
import android.widget.*
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
        val professionSpinner = root.findViewById<Spinner>(R.id.professionSpinner)
        val ageSpinner = root.findViewById<Spinner>(R.id.ageSpinner)

        val kins = resources.getStringArray(R.array.kin)
        val professions = resources.getStringArray(R.array.profession)
        val ages = resources.getStringArray(R.array.age)

        setSpinner(kinSpinner,kins);
        setSpinner(professionSpinner, professions);
        setSpinner(ageSpinner, ages);

        setKinListener(kinSpinner)

        return root
    }

    fun setSpinner(spinner: Spinner, options: Array<String>) {
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, options )


        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)
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