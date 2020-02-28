package com.robertferreira.forbiddenlandscharcreator.ui.diceroller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentDicerollerBinding

class DiceRollerFragment : Fragment() {

    lateinit var viewModel : DiceRollerViewModel
    lateinit var binding : FragmentDicerollerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DiceRollerViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diceroller, container, false)

        return binding.root
    }

}
