package com.robertferreira.forbiddenlandscharcreator.ui.diceroller

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.VISIBLE
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.DiceRollDialogBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentDicerollerBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.StepperRow
import kotlinx.android.synthetic.main.attribute_stepper.view.*

class DiceRollerFragment : Fragment() {

    lateinit var viewModel : DiceRollerViewModel
    lateinit var binding : FragmentDicerollerBinding
    lateinit var pop :ShowRoll

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DiceRollerViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diceroller, container, false)

        setStepper(binding.baseDiceStepper, binding.baseDiceLayout, R.drawable.dice_base_1, 0)
        setStepper(binding.skillDiceStepper, binding.skillDiceLayout, R.drawable.dice_skill_1, 1)
        setStepper(binding.gearDiceStepper, binding.gearDiceLayout, R.drawable.dice_gear_1, 2)
        //setStepper(binding.otherDiceStepper, binding.otherDiceLayout, R.drawable.dice_d8_1, 3)

        viewModel.bDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setD6Face(binding.baseDiceLayout[x] as ImageView,it[x],0)
            /*val total6 = it.filter{v -> v == 6}.sum()
            pop.binding.base6s.text = "6s: " + total6.toString()
            val total1 = it.filter{v -> v == 1}.sum()
            pop.binding.base6s.text = "1s: " + total1.toString()*/
        })
        viewModel.sDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setD6Face(binding.skillDiceLayout[x] as ImageView,it[x],1)
/*            val total6 = it.filter{v -> v == 6}.sum()
            pop.binding.skill6s.text = "6s: " + total6.toString()
            val total1 = it.filter{v -> v == 1}.sum()
            pop.binding.skill1s.text = "1s: " + total1.toString()*/
        })
        viewModel.gDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setD6Face(binding.gearDiceLayout[x] as ImageView,it[x],2)
            /*val total6 = it.filter{v -> v == 6}.sum()
            pop.binding.gear6s.text = "6s: " + total6.toString()
            val total1 = it.filter{v -> v == 1}.sum()
            pop.binding.gear1s.text = "1s: " + total1.toString()*/
        })
       /* viewModel.oDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setD6Face(binding.otherDiceLayout[x] as ImageView,it[x],3)
        })*/
        pop = ShowRoll()

        binding.rollButton.setOnClickListener {
            viewModel.rollDice()
            showDialog()
            binding.pushRollButton.visibility = View.VISIBLE
        }

        binding.pushRollButton.setOnClickListener {
            viewModel.pushRollDice()
        }

        return binding.root
    }

    private fun showDialog() {
        val fm = this.fragmentManager
        pop.show(fm, "showRoll")
    }

    fun setStepper(stepper: StepperRow, layout : LinearLayout, imageResource : Int , type: Int){

        stepper.stepper_add.setOnClickListener {
            var iv = ImageView(context)
            iv.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            iv.setImageResource(imageResource)

            // Creating a LinearLayout.LayoutParams object for text view
            val param = iv.layoutParams as LinearLayout.LayoutParams
            param.setMargins(8,8,8,8)
            param.weight = 1F
            iv.layoutParams = param
            viewModel.addDie(type)
            stepper.current_value++
            stepper.setText(stepper.current_value)
            layout.addView(iv)
        }
        stepper.stepper_remove.setOnClickListener {
            if (stepper.current_value > 0) {
                stepper.current_value--
                stepper.setText(stepper.current_value)
                if(layout.childCount > 0) {
                    layout.removeViewAt(layout.indexOfChild(layout.children.last()))
                    viewModel.removeDie(type)
                }
            }
        }
    }

    fun setD6Face(view: ImageView, value: Int, type: Int){
        when(value) {
            1 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_1)
                1 -> view.setImageResource(R.drawable.dice_skill_1)
                2 -> view.setImageResource(R.drawable.dice_gear_1)
                3 -> view.setImageResource(R.drawable.dice_d8_1)
            }
            2 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_2)
                1 -> view.setImageResource(R.drawable.dice_skill_2)
                2 -> view.setImageResource(R.drawable.dice_gear_2)
                3 -> view.setImageResource(R.drawable.dice_d8_2)
            }
            3 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_3)
                1 -> view.setImageResource(R.drawable.dice_skill_3)
                2 -> view.setImageResource(R.drawable.dice_gear_3)
                3 -> view.setImageResource(R.drawable.dice_d8_3)
            }
            4 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_4)
                1 -> view.setImageResource(R.drawable.dice_skill_4)
                2 -> view.setImageResource(R.drawable.dice_gear_4)
                3 -> view.setImageResource(R.drawable.dice_d8_4)
            }
            5 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_5)
                1 -> view.setImageResource(R.drawable.dice_skill_5)
                2 -> view.setImageResource(R.drawable.dice_gear_5)
                3 -> view.setImageResource(R.drawable.dice_d8_5)
            }
            6 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_6)
                1 -> view.setImageResource(R.drawable.dice_skill_6)
                2 -> view.setImageResource(R.drawable.dice_gear_6)
                3 -> view.setImageResource(R.drawable.dice_d8_6)
            }
        }
    }
}

class ShowRoll : DialogFragment() {
    lateinit var binding : DiceRollDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.dice_roll_dialog, container, false)


        return binding.root
    }}
