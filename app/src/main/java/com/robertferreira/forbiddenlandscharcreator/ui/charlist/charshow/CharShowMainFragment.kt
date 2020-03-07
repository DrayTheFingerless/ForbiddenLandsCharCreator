package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowMainBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.CharViewModel

class CharShowMainFragment : Fragment() {

    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph)
    lateinit var binding : FragmentCharShowMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_show_main, container, false)
        //viewModel = ViewModelProviders.of(this).get(CharShowViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_PAGE) }?.apply {

        }
    }
}
