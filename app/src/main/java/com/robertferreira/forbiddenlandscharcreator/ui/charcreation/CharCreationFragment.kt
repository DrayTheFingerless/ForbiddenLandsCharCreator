package com.robertferreira.forbiddenlandscharcreator.ui.charcreation
import android.R.layout
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.Ages
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent


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

        //example view model bind. get text view, link textview value with Observer
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
        professionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                charCreationViewModel.getFilteredProfessionTalents(-1)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProfession = professionSpinner.selectedItem as Profession
                charCreationViewModel.getFilteredProfessionTalents(selectedProfession.ProfessionId)
            }
        }

        val ageSpinner = root.findViewById<Spinner>(R.id.ageSpinner)
        val ageAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Ages.ages )
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner!!.setAdapter(ageAdapter)

        val profTalentSpinner = root.findViewById<Spinner>(R.id.prof_talent_spinner)
//        var profTalentAdapter = ArrayAdapter<Talent>(this.requireContext(), android.R.layout.simple_spinner_item )
        val profTalentAdapter: ArrayAdapter<Talent> = object : ArrayAdapter<Talent>(
            this.requireContext(),
            android.R.layout.simple_spinner_item) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    (v.findViewById<View>(android.R.id.text1) as TextView).text = ""
                    (v.findViewById<View>(android.R.id.text1) as TextView).hint =
                       "Profession Talent" //"Hint to be displayed"
                }
                return v
            }

            override fun getCount(): Int {
                return super.getCount() - 1 // you dont display last item. It is used as hint.
            }
        }
        profTalentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        profTalentSpinner!!.setAdapter(profTalentAdapter)
        charCreationViewModel.pTalents.observe(this, Observer {
            Log.i("filtered talents0", it.count().toString())
            profTalentAdapter.clear()
            profTalentAdapter.addAll(it)
        })

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