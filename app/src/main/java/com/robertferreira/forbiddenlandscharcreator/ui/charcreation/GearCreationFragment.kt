package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentGearCreationBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect.GearSelectViewModel

class GearCreationFragment : Fragment() {
    private lateinit var binding : FragmentGearCreationBinding
    private val gearSelectViewModel: GearSelectViewModel by activityViewModels()

    private val viewModel: CharViewModel by navGraphViewModels(R.id.char_creation_nav_graph){
        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        CharCreationViewModelFactory(dataSource, application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_gear_creation,
            container,
            false
        )
        binding.charViewModel = viewModel

        val adapter = GearListAdapter(GearListAdapter.GearListener {
                g ->  viewModel.gearClicked(g)
        }, GearListAdapter.DeleteGearListener{
                g -> viewModel.removeGearClicked(g) })
        binding.gearList.adapter = adapter


        //update list when new gear
        gearSelectViewModel.newGear.observe(viewLifecycleOwner, Observer {
            if(it) {
                gearSelectViewModel.gearSelected.value?.let { t ->
                    viewModel.addGear(t)
                }
                gearSelectViewModel.newGear.value = false
            }
        })

        viewModel.char.observe(viewLifecycleOwner, Observer{
            it.Gear?.let { gear ->
                adapter.data = gear
                adapter.notifyDataSetChanged()
            }
        })

        //gear points
        viewModel.maxWeight.observe(viewLifecycleOwner, Observer{
            binding.weightMax.text = it.toString()
        })

        binding.addGearButton.setOnClickListener{
            viewModel.tryAddGear()
        }
        viewModel.navigateToGearSelect.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.action_new_gear_to_gear_select)
                viewModel.navigateToGearSelect.value = false
            }
        })

        binding.nextButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_gear_to_info))


        return binding.root
    }
}
