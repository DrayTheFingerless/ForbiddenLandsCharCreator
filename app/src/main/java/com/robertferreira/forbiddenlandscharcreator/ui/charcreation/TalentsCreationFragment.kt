package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.InfoCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.TalentsCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentSelectFragment
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentSelectViewModel

class TalentsCreationFragment : Fragment() {

    private lateinit var binding : TalentsCreationFragmentBinding

    private val viewModel: CharViewModel by navGraphViewModels(R.id.char_creation_nav_graph){
        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        CharCreationViewModelFactory(dataSource, application)
    }
    private val tSelectViewModel : TalentSelectViewModel  by navGraphViewModels(R.id.char_creation_nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.talents_creation_fragment,
            container,
            false
        )

        binding.charViewModel = viewModel

        val adapter = TalentsListAdapter(TalentsListAdapter.TalentListener {
                tId ->  viewModel.talentClicked(tId)
        }, TalentsListAdapter.RemoveListener{
                tId -> viewModel.removeClicked(tId)
        })
        binding.talentsList.adapter = adapter



        tSelectViewModel.talentSelected.observe(viewLifecycleOwner, Observer {
            viewModel.addTalent(it)
        })

        viewModel.char.observe(viewLifecycleOwner, Observer {
            it.TalentList?.let { tlts ->
                adapter.data = tlts}
        })

        binding.addTalentButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_talents_to_select_new))

        binding.nextButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_talents_to_info))

        return binding.root
    }
}
