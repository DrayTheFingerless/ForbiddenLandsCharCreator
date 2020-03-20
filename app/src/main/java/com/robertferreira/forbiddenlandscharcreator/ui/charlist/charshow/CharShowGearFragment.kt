package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowGearBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentGearShowDialogBinding
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.GearListAdapter
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect.GearSelectViewModel


class CharShowGearFragment : Fragment() {
    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph){
        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        CharShowViewModelFactory(dataSource, application)
    }
    private val gearSelectViewModel: GearSelectViewModel by activityViewModels()
    lateinit var binding : FragmentCharShowGearBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_show_gear, container, false)
        binding.viewModel = viewModel


        val adapter = GearListAdapter(GearListAdapter.GearListener {
                tId ->  viewModel.gearClicked(tId)
        }, GearListAdapter.DeleteGearListener{
                g -> viewModel.removeGearClicked(g) })
        binding.gearList.adapter = adapter



        viewModel.showGear.observe(viewLifecycleOwner, Observer {
            if(it) {
                viewModel.gClicked.value?.let { gear ->
                    val dialog = GearShowDialogFragment.newInstance(gear)
                    dialog.show(childFragmentManager, "dialog")
                }
                viewModel.showGear.value = false
            }
        })

        //update list when new gear
        gearSelectViewModel.newGear.observe(viewLifecycleOwner, Observer {
            if(it) {
                gearSelectViewModel.gearSelected.value?.let { t ->
                    viewModel.addGear(t)
                }
                gearSelectViewModel.newGear.value = false
            }
        })
        viewModel.character.observe(viewLifecycleOwner, Observer {
            it.Gear?.let { gear ->
                adapter.data = gear
                adapter.notifyDataSetChanged()
            }
        })
        binding.addGearButton.setOnClickListener{
            viewModel.tryAddGear()
        }
        viewModel.navigateToGearSelect.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.action_show_to_gear_select)
                viewModel.navigateToGearSelect.value = false
            }
        })

        return binding.root
    }

}

class GearShowDialogFragment : DialogFragment() {
    lateinit var binding : FragmentGearShowDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gear_show_dialog, container, false)


        binding.gearName.text =  arguments?.getString(GEAR).toString() ?: throw IllegalStateException("No args provided")
        arguments?.getInt(COST)?.let {
            if(it > 9)
                binding.costText.text = (it / 10).toString() + " Silver"
            else binding.costText.text = it.toString() + " Copper"
        }
        binding.weightText.text =  arguments?.getString(WEIGHT).toString() ?: throw IllegalStateException("No args provided")
        binding.effect.text =  arguments?.getString(EFFECT).toString() ?: throw IllegalStateException("No args provided")

        return binding.root
    }
    companion object {

        private const val GEAR = "Gear"
        private const val COST = "Cost"
        private const val WEIGHT = "Weight"
        private const val EFFECT = "Effect"

        fun newInstance(
            gear: Gear
        ): GearShowDialogFragment = GearShowDialogFragment().apply {
            arguments = Bundle().apply {
                putString(GEAR, gear.name)
                putInt(COST, gear.cost)
                putString(WEIGHT, gear.weight.toString())
                putString(EFFECT, gear.features.joinToString(", "))

            }
        }
    }
}