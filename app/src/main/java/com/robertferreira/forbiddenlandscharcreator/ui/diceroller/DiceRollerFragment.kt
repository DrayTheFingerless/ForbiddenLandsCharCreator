package com.robertferreira.forbiddenlandscharcreator.ui.diceroller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentDicerollerBinding
import kotlinx.android.synthetic.main.attribute_stepper.view.*

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

        setSteppers()

        return binding.root
    }


    fun setSteppers(){

        binding.baseDiceStepper.stepper_add.setOnClickListener {
            var iv = ImageView(context)
            iv.layoutParams = LinearLayout.LayoutParams(50, 50)
            iv.setImageResource(R.drawable.dice_base_1)
            // Creating a LinearLayout.LayoutParams object for text view
            val param = iv.layoutParams as LinearLayout.LayoutParams
            param.setMargins(8,8,8,8)
            iv.layoutParams = param
            viewModel.addDie(0)

            binding.baseDiceStepper.current_value++
            binding.showDiceLayout.addView(iv)
        }
        binding.baseDiceStepper.stepper_remove.setOnClickListener {
            if (binding.baseDiceStepper.current_value > 0) {
                binding.baseDiceStepper.current_value--
                if(binding.showDiceLayout.childCount > 0) {
                    binding.showDiceLayout.removeViewAt(0)
                    viewModel.removeDie(0)
                }
            }
        }
    }


}
