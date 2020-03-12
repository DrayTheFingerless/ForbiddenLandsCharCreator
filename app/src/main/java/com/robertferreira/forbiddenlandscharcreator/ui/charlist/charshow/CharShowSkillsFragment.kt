package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowMainBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowSkillsBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.CharViewModel


class CharShowSkillsFragment : Fragment() {

    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph)
    lateinit var binding : FragmentCharShowSkillsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_show_skills, container, false)
        //viewModel = ViewModelProviders.of(this).get(CharShowViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        setRollButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_PAGE) }?.apply {

        }
    }

    fun setRollButtons(){
        binding.rollMight.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentStrength.value ?: 2)
            bundle.putInt("skill", viewModel.charMight.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollEndurance.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentStrength.value ?: 2)
            bundle.putInt("skill", viewModel.charEndurance.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollMelee.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentStrength.value ?: 2)
            bundle.putInt("skill", viewModel.charMelee.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollCrafting.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentStrength.value ?: 2)
            bundle.putInt("skill", viewModel.charCraft.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollStealth.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentAgility.value ?: 2)
            bundle.putInt("skill", viewModel.charStealth.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollSleight.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentAgility.value ?: 2)
            bundle.putInt("skill", viewModel.charSleight.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollMove.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentAgility.value ?: 2)
            bundle.putInt("skill", viewModel.charMove.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollMarksmanship.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentAgility.value ?: 2)
            bundle.putInt("skill", viewModel.charMarksman.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollScouting.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentWits.value ?: 2)
            bundle.putInt("skill", viewModel.charScout.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollLore.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentWits.value ?: 2)
            bundle.putInt("skill", viewModel.charWits.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollSurvival.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentWits.value ?: 2)
            bundle.putInt("skill", viewModel.charSurvival.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollInsight.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentWits.value ?: 2)
            bundle.putInt("skill", viewModel.charInsight.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollManipulation.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentEmpathy.value ?: 2)
            bundle.putInt("skill", viewModel.charManipulation.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollPerformance.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentEmpathy.value ?: 2)
            bundle.putInt("skill", viewModel.charPerfomance.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollHealing.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentEmpathy.value ?: 2)
            bundle.putInt("skill", viewModel.charHealing.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
        binding.rollAnimal.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("base", viewModel.charCurrentEmpathy.value ?: 2)
            bundle.putInt("skill", viewModel.charAnimal.value ?: 0)

            findNavController().navigate(R.id.action_show_to_dice, bundle)
        }
    }
}
