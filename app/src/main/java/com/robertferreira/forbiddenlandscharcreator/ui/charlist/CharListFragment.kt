package com.robertferreira.forbiddenlandscharcreator.ui.charlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharcreationBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharlistBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charlist.CharListAdapter.CharacterListener

class CharListFragment : Fragment() {


    private lateinit var charListViewModel: CharListViewModel

    private lateinit var binding : FragmentCharlistBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charlist, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        val viewModelFactory = CharListViewModelFactory(dataSource, application)

        charListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharListViewModel::class.java)

        binding.viewModel = charListViewModel

        val adapter = CharListAdapter(CharacterListener {
                charId ->  charListViewModel.onCharacterClicked(charId)
        })
        binding.charList.adapter = adapter

        charListViewModel.charList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        charListViewModel.navigateToCharacter.observe(viewLifecycleOwner, Observer {char ->
            char?.let {
                val bundle = Bundle()
                bundle.putParcelable("character", it)
                this.findNavController().navigate(R.id.action_list_to_show, bundle)
                charListViewModel.onCharacterNavigated()
            }
        })

        return binding.root
    }
}