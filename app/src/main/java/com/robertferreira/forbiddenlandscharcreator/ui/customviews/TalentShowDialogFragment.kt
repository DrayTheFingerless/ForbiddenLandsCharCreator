package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentTalentShowDialogBinding

class TalentShowDialogFragment : DialogFragment() {
    lateinit var binding : FragmentTalentShowDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talent_show_dialog, container, false)


        binding.talentName.text =  arguments?.getString(NAME).toString() ?: throw IllegalStateException("No args provided")
        binding.description.text =  arguments?.getString(DESCRIPTION).toString() ?: throw IllegalStateException("No args provided")
        binding.rank1Text.text =  arguments?.getString(RANK1).toString() ?: throw IllegalStateException("No args provided")
        binding.rank2Text.text =  arguments?.getString(RANK2).toString() ?: throw IllegalStateException("No args provided")
        binding.rank3Text.text =  arguments?.getString(RANK3).toString() ?: throw IllegalStateException("No args provided")

        return binding.root
    }
    companion object {

        private const val NAME = "Name"
        private const val DESCRIPTION = "Description"
        private const val RANK1 = "Rank1"
        private const val RANK2 = "Rank2"
        private const val RANK3 = "Rank3"

        fun newInstance(
            talent: Talent
        ): TalentShowDialogFragment = TalentShowDialogFragment().apply {
            arguments = Bundle().apply {
                putString(NAME, talent.name)
                putString(DESCRIPTION, talent.description)
                putString(RANK1, talent.rank_1)
                putString(RANK2, talent.rank_2)
                putString(RANK3, talent.rank_3)

            }
        }
    }
}
