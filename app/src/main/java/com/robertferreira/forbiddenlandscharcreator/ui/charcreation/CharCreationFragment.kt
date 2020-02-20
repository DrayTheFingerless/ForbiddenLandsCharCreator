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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.Ages
import com.robertferreira.forbiddenlandscharcreator.Kin
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharcreationBinding
import kotlinx.android.synthetic.main.fragment_charcreation.*


class CharCreationFragment : Fragment() {

    private lateinit var viewModel: CharCreationViewModel

    private lateinit var binding : FragmentCharcreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(viewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charcreation, container, false)

        //example view model bind. get text view, link textview value with Observer
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        viewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        //setup simple adapter for kin spinner with boilerplate kins array
        val kinAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Kins.kins )
        kinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.kinSpinner!!.setAdapter(kinAdapter)
        setKinListener(binding.kinSpinner)

        viewModel.kinTalentName.observe(this, Observer {
            binding.kinTalentDisplay.text = it
        })
        val professionAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Professions.professions )
        professionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.professionSpinner!!.setAdapter(professionAdapter)
        binding.professionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.getFilteredProfessionTalents(-1)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProfession = professionSpinner.selectedItem as Profession
                viewModel.getFilteredProfessionTalents(selectedProfession.ProfessionId)
            }
        }

        val ageAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Ages.ages )
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ageSpinner!!.setAdapter(ageAdapter)

        var profTalentAdapter = ArrayAdapter<Talent>(this.requireContext(), android.R.layout.simple_spinner_item )
        profTalentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.profTalentSpinner!!.setAdapter(profTalentAdapter)
        viewModel.pTalents.observe(this, Observer {
            Log.i("filtered talents0", it.count().toString())
            profTalentAdapter.clear()
            profTalentAdapter.addAll(it)
        })

        return binding.root
    }

    fun setKinListener(kinSpinner : Spinner) {
       var listener = object: AdapterView.OnItemSelectedListener {
           override fun onNothingSelected(parent: AdapterView<*>?) {
                //clear Prime Kin Attribute
               viewModel.ClearKin()
               kin_talent_display.text = getString(R.string.none)
           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               // **alter Prime Kin Attribute based on selected Kin**
               viewModel.SelectKin(position)
           }
       }

        kinSpinner.onItemSelectedListener = listener
    }


}