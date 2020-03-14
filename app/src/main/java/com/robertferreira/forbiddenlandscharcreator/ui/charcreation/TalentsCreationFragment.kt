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
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentShowDialogFragment

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
                tId -> viewModel.removeTClicked(tId)
        },TalentsListAdapter.AddListener{
                tId -> viewModel.addTClicked(tId)
        }, false)
        binding.talentsList.adapter = adapter


        //talent points
        viewModel.talentPoints.observe(viewLifecycleOwner, Observer{
            binding.talentPoints.text = it.toString()
        })

        //show pop up of talent
        viewModel.showTalent.observe(viewLifecycleOwner, Observer {
            if(it) {
                viewModel.tClicked.value?.let { talent ->
                    val dialog = TalentShowDialogFragment.newInstance(talent)
                    dialog.show(childFragmentManager, "dialog")
                }
                viewModel.showTalent.value = false
            }
        })

        //update list when new talent
        tSelectViewModel.newTalent.observe(viewLifecycleOwner, Observer {
            if(it) {
                tSelectViewModel.talentSelected.value?.let { t ->
                    viewModel.addTalent(t)
                }
                tSelectViewModel.newTalent.value = false
            }
        })


        viewModel.char.observe(viewLifecycleOwner, Observer {
            it.TalentList?.let { tlts ->
                adapter.data = tlts
                adapter.notifyDataSetChanged()
            }
        })

        //navigate to talent select
        binding.addTalentButton.setOnClickListener{
            viewModel.tryAddTalent()
        }
        viewModel.navigateToAddTalent.observe(viewLifecycleOwner, Observer {
            if(it) {
                findNavController().navigate(R.id.action_talents_to_select_new)
                viewModel.navigateToAddTalent.value = false
            }
        })

        binding.nextButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_talent_to_gear))


            return binding.root
        }
    }
