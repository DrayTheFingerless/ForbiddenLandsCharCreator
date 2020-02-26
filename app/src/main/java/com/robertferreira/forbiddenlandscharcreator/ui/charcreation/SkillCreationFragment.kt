package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.robertferreira.forbiddenlandscharcreator.Attributes

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.databinding.SkillCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.StepperRow
import kotlinx.android.synthetic.main.attribute_stepper.view.*

class SkillCreationFragment : Fragment() {

    companion object {
        fun newInstance() = SkillCreationFragment()
    }

    private lateinit var viewModel: SkillCreationViewModel


    private lateinit var binding : SkillCreationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charcreation, container, false)

        setObservers()
        setSteppers()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SkillCreationViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun setObservers(){
        //skills
        viewModel.skillPoints.observe(viewLifecycleOwner, Observer{
            binding.skillPoints.text = it.toString()
        })
        viewModel.charMight.observe(viewLifecycleOwner,Observer{
            binding.mightStepper.setText(it)
        })
        viewModel.charEndurance.observe(viewLifecycleOwner,Observer{
            binding.enduranceStepper.setText(it)
        })
        viewModel.charCraft.observe(viewLifecycleOwner,Observer{
            binding.craftStepper.setText(it)
        })
        viewModel.charMelee.observe(viewLifecycleOwner,Observer{
            binding.meleeStepper.setText(it)
        })
        viewModel.charStealth.observe(viewLifecycleOwner,Observer{
            binding.stealthStepper.setText(it)
        })
        viewModel.charSleight.observe(viewLifecycleOwner,Observer{
            binding.sleightStepper.setText(it)
        })
        viewModel.charMove.observe(viewLifecycleOwner,Observer{
            binding.moveStepper.setText(it)
        })
        viewModel.charMarksman.observe(viewLifecycleOwner,Observer{
            binding.marksmanshipStepper.setText(it)
        })
        viewModel.charScout.observe(viewLifecycleOwner,Observer{
            binding.scoutingStepper.setText(it)
        })
        viewModel.charLore.observe(viewLifecycleOwner,Observer{
            binding.loreStepper.setText(it)
        })
        viewModel.charSurvival.observe(viewLifecycleOwner,Observer{
            binding.survivalStepper.setText(it)
        })
        viewModel.charInsight.observe(viewLifecycleOwner,Observer{
            binding.insightStepper.setText(it)
        })
        viewModel.charManipulation.observe(viewLifecycleOwner,Observer{
            binding.manipulationStepper.setText(it)
        })
        viewModel.charPerfomance.observe(viewLifecycleOwner,Observer{
            binding.performanceStepper.setText(it)
        })
        viewModel.charHealing.observe(viewLifecycleOwner,Observer{
            binding.healingStepper.setText(it)
        })
        viewModel.charAnimal.observe(viewLifecycleOwner,Observer{
            binding.animalStepper.setText(it)
        })
    }

    fun setSteppers(){

        setSkillStepperListener(binding.mightStepper, Skills.Might)
        setSkillStepperListener(binding.enduranceStepper, Skills.Endurance)
        setSkillStepperListener(binding.meleeStepper, Skills.Melee)
        setSkillStepperListener(binding.craftStepper, Skills.Crafting)
        setSkillStepperListener(binding.stealthStepper, Skills.Stealth)
        setSkillStepperListener(binding.sleightStepper, Skills.SleightOfHand)
        setSkillStepperListener(binding.moveStepper, Skills.Move)
        setSkillStepperListener(binding.marksmanshipStepper, Skills.Marksmanship)
        setSkillStepperListener(binding.scoutingStepper, Skills.Scouting)
        setSkillStepperListener(binding.loreStepper, Skills.Lore)
        setSkillStepperListener(binding.survivalStepper, Skills.Survival)
        setSkillStepperListener(binding.insightStepper, Skills.Insight)
        setSkillStepperListener(binding.manipulationStepper, Skills.Manipulation)
        setSkillStepperListener(binding.performanceStepper, Skills.Performance)
        setSkillStepperListener(binding.healingStepper, Skills.Healing)
        setSkillStepperListener(binding.animalStepper, Skills.AnimalHandling)

    }

    fun setSkillStepperListener(st: StepperRow, skill : Skills){
        st.stepper_remove.setOnClickListener {
            /* if(st.current_value > st.minimum_value){
                 st.stepper_add.isEnabled = true
             }
             else st.stepper_remove.isEnabled = false*/
            viewModel.ChangeSkill(skill, false)
        }
        st.stepper_add.setOnClickListener {
            /*if(st.current_value <  st.max_value){
                st.stepper_remove.isEnabled = true
            }
            else st.stepper_add.isEnabled = false*/
            viewModel.ChangeSkill(skill, true)
        }
    }


}
