package com.robertferreira.forbiddenlandscharcreator.ui.charcreation
import android.R.layout
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.robertferreira.forbiddenlandscharcreator.Ages
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Kin
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharcreationBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.StepperRow
import kotlinx.android.synthetic.main.attribute_stepper.*
import kotlinx.android.synthetic.main.attribute_stepper.view.*
import kotlinx.android.synthetic.main.fragment_charcreation.*


class CharCreationFragment : Fragment() {

    private lateinit var viewModel: CharCreationViewModel

    private lateinit var binding : FragmentCharcreationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CharCreationViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charcreation, container, false)


        binding.newNextButton.setOnClickListener {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/

            var bundle = bundleOf("char" to viewModel.char.value)

            Navigation.findNavController(it).navigate(R.id.action_new_to_skills, bundle);
        }

        //setup simple adapter for kin spinner with hard coded kins array
        val kinAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Kins.kins )
        kinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.kinSpinner!!.setAdapter(kinAdapter)
        setKinListener(binding.kinSpinner)


        val professionAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Professions.professions )
        professionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.professionSpinner!!.setAdapter(professionAdapter)
        binding.professionSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.SelectProfession(-1)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedProfession = professionSpinner.selectedItem as Profession
                viewModel.SelectProfession(position)
            }
        }

        val ageAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, Ages.ages )
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ageSpinner!!.setAdapter(ageAdapter)
        binding.ageSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.SelectAge(-1, 0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var num = 0
                try {
                    num = ageNumber.text.toString().toInt()
                } catch(nfe: NumberFormatException){
                    print(nfe)
                }
                viewModel.SelectAge(position, num)
            }
        }

        var profTalentAdapter = ArrayAdapter<Talent>(this.requireContext(), android.R.layout.simple_spinner_item )
        profTalentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.profTalentSpinner!!.setAdapter(profTalentAdapter)
        viewModel.pTalents.observe(viewLifecycleOwner, Observer {
            Log.i("filtered talents0", it.count().toString())
            profTalentAdapter.clear()
            profTalentAdapter.addAll(it)
        })

        //hook up steppers
        setObservers()
        setSteppers()

        return binding.root
    }

    fun setKinListener(kinSpinner : Spinner) {
       var listener = object: AdapterView.OnItemSelectedListener {
           override fun onNothingSelected(parent: AdapterView<*>?) {
                //clear Prime Kin Attribute
               kin_talent_display.text = getString(R.string.none)
           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               // **alter Prime Kin Attribute based on selected Kin**
               viewModel.SelectKin(position)
           }
       }

        kinSpinner.onItemSelectedListener = listener
    }

    fun setObservers(){
        //kin Talent
        viewModel.kinTalentName.observe(viewLifecycleOwner, Observer {
            binding.kinTalentDisplay.text = it
        })


        //attributes
        viewModel.attrPoints.observe(viewLifecycleOwner, Observer{
            binding.attributePoints.text = it.toString()
        })
        viewModel.charStrength.observe(viewLifecycleOwner,Observer{
            binding.strengthStepper.setText(it)
        })

        viewModel.charAgility.observe(viewLifecycleOwner,Observer{
            binding.agilityStepper.setText(it)
        })

        viewModel.charWits.observe(viewLifecycleOwner,Observer{
            binding.witsStepper.setText(it)
        })

        viewModel.charEmpathy.observe(viewLifecycleOwner,Observer{
            binding.empathyStepper.setText(it)
        })


    }

    fun setSteppers(){
        setAttributeStepperListener(binding.strengthStepper, Attributes.Strength)
        setAttributeStepperListener(binding.agilityStepper, Attributes.Agility)
        setAttributeStepperListener(binding.witsStepper, Attributes.Wits)
        setAttributeStepperListener(binding.empathyStepper, Attributes.Empathy)


    }

    fun setAttributeStepperListener(st: StepperRow, attribute : Attributes){
        st.stepper_remove.setOnClickListener {
            /*if(st.current_value > st.minimum_value){
                st.stepper_add.isEnabled = true
            }
            else  st.stepper_remove.isEnabled = false*/
            viewModel.DecrementAttribute(attribute)
        }
        st.stepper_add.setOnClickListener {
            /*if(st.current_value <  st.max_value){
                st.stepper_remove.isEnabled = true
            }
            else st.stepper_add.isEnabled = false*/
            viewModel.IncrementAttribute(attribute)
        }
    }

}