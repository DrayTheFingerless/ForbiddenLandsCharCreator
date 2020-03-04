package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.BeginCharCreationFragmentBinding

class BeginCharCreationFragment : Fragment() {

    companion object {
        fun newInstance() = BeginCharCreationFragment()
    }

    private lateinit var viewModel: BeginCharCreationViewModel
    private lateinit var binding : BeginCharCreationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.begin_char_creation_fragment, container, false)

        binding.startButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_begin_to_new)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeginCharCreationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
