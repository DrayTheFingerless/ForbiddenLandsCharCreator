package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentGearCreationBinding

class GearCreationFragment : Fragment() {
    private lateinit var binding : FragmentGearCreationBinding

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
                tId ->  viewModel.gearClicked(tId)
        }, GearListAdapter.DeleteGearListener{
                tId -> viewModel.removeGearClicked(tId) })
        binding.gearList.adapter = adapter


        viewModel.char.observe(viewLifecycleOwner, Observer{
            it.Gear?.let { gear ->
                adapter.data = gear
                adapter.notifyDataSetChanged()
            }
        })

        return binding.root
    }
}
