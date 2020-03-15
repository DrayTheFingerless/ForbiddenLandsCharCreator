package com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentTalentSelectListBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.GearSelectFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentSelectViewModel

class GearSelectFragment : Fragment() {

    companion object {
        fun newInstance() = GearSelectFragment()
    }

    private lateinit var viewModel: GearSelectViewModel

    lateinit var binding : GearSelectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.gear_select_fragment,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(GearSelectViewModel::class.java)

        val adapter = MyGearSelectListAdapter(MyGearSelectListAdapter.GearSelectListener { id ->
            viewModel.setGear(id)
            findNavController().navigateUp()
        })

        binding.gearList.adapter = adapter

        viewModel.gearList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        return binding.root
    }
}
