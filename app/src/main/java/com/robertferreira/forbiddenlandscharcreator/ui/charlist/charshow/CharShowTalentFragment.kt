package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowMainBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowTalentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.TalentsListAdapter
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentShowDialogFragment

class CharShowTalentFragment : Fragment() {

    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph){
        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        CharShowViewModelFactory(dataSource, application)
    }
    lateinit var binding : FragmentCharShowTalentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_show_talent, container, false)
        //viewModel = ViewModelProviders.of(this).get(CharShowViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        val adapter = TalentsListAdapter(TalentsListAdapter.TalentListener {
            tId ->  viewModel.talentClicked(tId)
        }, TalentsListAdapter.RemoveListener{
            tId -> viewModel.removeTClicked(tId)
        },TalentsListAdapter.AddListener{
            tId ->  viewModel.addTClicked(tId)
        },  true)
        binding.talentsList.adapter = adapter


        viewModel.showTalent.observe(viewLifecycleOwner, Observer {
            if(it) {
                viewModel.tClicked.value?.let { talent ->
                    val dialog = TalentShowDialogFragment.newInstance(talent)
                    dialog.show(childFragmentManager, "dialog")
                }
                viewModel.showTalent.value = false
            }
        })

        viewModel.character.observe(viewLifecycleOwner, Observer {
            it.TalentList?.let { tlts ->
                adapter.data = tlts
                adapter.notifyDataSetChanged()
            }
        })

        return binding.root
    }
}
