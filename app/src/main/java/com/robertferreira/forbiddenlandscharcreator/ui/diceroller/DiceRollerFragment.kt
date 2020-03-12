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
    var base6  : Int = 0
    var base1 : Int = 0
    var skill6 : Int= 0
    var skill1 : Int= 0
    var gear6 : Int= 0
    var gear1 : Int= 0
    var d8success : Int= 0
    var d8banes : Int= 0
    var d10success : Int= 0
    var d10banes : Int= 0
    var d12success : Int= 0
    var d12banes : Int= 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DiceRollerViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diceroller, container, false)
        pop = ShowRoll()



        setStepper(binding.baseDiceStepper, binding.baseDiceLayout, R.drawable.dice_base_1, 0)
        setStepper(binding.skillDiceStepper, binding.skillDiceLayout, R.drawable.dice_skill_1, 1)
        setStepper(binding.gearDiceStepper, binding.gearDiceLayout, R.drawable.dice_gear_1, 2)
        setStepper(binding.d8Stepper, binding.d8DiceLayout, R.drawable.dice_d8_1, 3)
        setStepper(binding.d10Stepper, binding.d10DiceLayout, R.drawable.dice_d10_1, 4)
        setStepper(binding.d12Stepper, binding.d12DiceLayout, R.drawable.dice_d12_1, 5)

        //setStepper(binding.otherDiceStepper, binding.otherDiceLayout, R.drawable.dice_d8_1, 3)

        viewModel.bDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.baseDiceLayout[x] as ImageView,it[x],0)
            base6 = it.filter{v -> v == 6}.count()
            base1 = it.filter{v -> v == 1}.count()
        })
        viewModel.sDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.skillDiceLayout[x] as ImageView,it[x],1)
            skill6 = it.filter{v -> v == 6}.count()
            skill1 = it.filter{v -> v == 1}.count()
        })
        viewModel.gDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.gearDiceLayout[x] as ImageView,it[x],2)
            gear6 = it.filter{v -> v == 6}.count()
            gear1 = it.filter{v -> v == 1}.count()
        })
        viewModel.d8Dice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.d8DiceLayout[x] as ImageView,it[x],3)
            d8success = countSuccesses(it)
            d8banes = it.filter{v -> v == 1}.count()
        })
        viewModel.d10Dice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.d10DiceLayout[x] as ImageView,it[x],4)
            d10success = countSuccesses(it)
            d10banes = it.filter{v -> v == 1}.count()
        })
        viewModel.d12Dice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setDiceFace(binding.d12DiceLayout[x] as ImageView,it[x],5)
            d12success = countSuccesses(it)
            d12banes = it.filter{v -> v == 1}.count()
        })
       /* viewModel.oDice.observe(viewLifecycleOwner, Observer{
            for (x in 0 until it.count())
                setD6Face(binding.otherDiceLayout[x] as ImageView,it[x],3)
        })*/


        viewModel.diceRolled.observe(viewLifecycleOwner, Observer { rolled ->
            if(rolled){
                val dialog = ShowRoll.newInstance(base6,base1,skill6,skill1,gear6,gear1, d8success+d10success+d12success,d8banes+d10banes+d12banes, false)
                dialog.show(childFragmentManager, "dialog")
                viewModel.onDiceRolled()
            }
        })

        viewModel.dicePushRolled.observe(viewLifecycleOwner, Observer { rolled ->
            if(rolled){
                val dialog = ShowRoll.newInstance(base6,base1,skill6,skill1,gear6,gear1, d8success+d10success+d12success,d8banes+d10banes+d12banes, true)
                dialog.show(childFragmentManager, "dialog")
                viewModel.onDiceRolled()
            }
        })

        binding.rollButton.setOnClickListener {
            viewModel.rollDice()
            binding.pushRollButton.visibility = View.VISIBLE
        }

        binding.pushRollButton.setOnClickListener {
            viewModel.pushRollDice()
        }

        arguments?.let{ arg ->
            var numBase = arguments?.getInt("base")
            var numSkill = arguments?.getInt("skills")
            var numGear = arguments?.getInt("gear")
            var num8 = arguments?.getInt("d8")
            var num10 = arguments?.getInt("d10")
            var num12 = arguments?.getInt("d12")

            numBase?.let{ d ->
                for(x in 0 ..(d-1))
                    viewModel.addDie(0)
            }
            numSkill?.let{ d ->
                for(x in 0 ..(d-1))
                    viewModel.addDie(1)
            }
            numGear?.let{ d ->
                for(x in 0 ..(d-1))
                    viewModel.addDie(2)
            }
        }
        return binding.root
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

    fun setDiceFace(view: ImageView, value: Int, type: Int){
        when(value) {
            1 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_1)
                1 -> view.setImageResource(R.drawable.dice_skill_1)
                2 -> view.setImageResource(R.drawable.dice_gear_1)
                3 -> view.setImageResource(R.drawable.dice_d8_1)
                4 -> view.setImageResource(R.drawable.dice_d10_1)
                5 -> view.setImageResource(R.drawable.dice_d12_1)
            }
            2 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_2)
                1 -> view.setImageResource(R.drawable.dice_skill_2)
                2 -> view.setImageResource(R.drawable.dice_gear_2)
                3 -> view.setImageResource(R.drawable.dice_d8_2)
                4 -> view.setImageResource(R.drawable.dice_d10_2)
                5 -> view.setImageResource(R.drawable.dice_d12_2)
            }
            3 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_3)
                1 -> view.setImageResource(R.drawable.dice_skill_3)
                2 -> view.setImageResource(R.drawable.dice_gear_3)
                3 -> view.setImageResource(R.drawable.dice_d8_3)
                4 -> view.setImageResource(R.drawable.dice_d10_3)
                5 -> view.setImageResource(R.drawable.dice_d12_3)
            }
            4 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_4)
                1 -> view.setImageResource(R.drawable.dice_skill_4)
                2 -> view.setImageResource(R.drawable.dice_gear_4)
                3 -> view.setImageResource(R.drawable.dice_d8_4)
                4 -> view.setImageResource(R.drawable.dice_d10_4)
                5 -> view.setImageResource(R.drawable.dice_d12_4)
            }
            5 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_5)
                1 -> view.setImageResource(R.drawable.dice_skill_5)
                2 -> view.setImageResource(R.drawable.dice_gear_5)
                3 -> view.setImageResource(R.drawable.dice_d8_5)
                4 -> view.setImageResource(R.drawable.dice_d10_5)
                5 -> view.setImageResource(R.drawable.dice_d12_5)
            }
            6 -> when(type) {
                0 -> view.setImageResource(R.drawable.dice_base_6)
                1 -> view.setImageResource(R.drawable.dice_skill_6)
                2 -> view.setImageResource(R.drawable.dice_gear_6)
                3 -> view.setImageResource(R.drawable.dice_d8_6)
                4 -> view.setImageResource(R.drawable.dice_d10_6)
                5 -> view.setImageResource(R.drawable.dice_d12_6)
            }
            7 -> when(type) {
                3 -> view.setImageResource(R.drawable.dice_d8_7)
                4 -> view.setImageResource(R.drawable.dice_d10_7)
                5 -> view.setImageResource(R.drawable.dice_d12_7)
            }
            8 -> when(type) {
                3 -> view.setImageResource(R.drawable.dice_d8_8)
                4 -> view.setImageResource(R.drawable.dice_d10_8)
                5 -> view.setImageResource(R.drawable.dice_d12_8)
            }
            9 -> when(type) {
                4 -> view.setImageResource(R.drawable.dice_d10_9)
                5 -> view.setImageResource(R.drawable.dice_d12_9)
            }
            10 -> when(type) {
                4 -> view.setImageResource(R.drawable.dice_d10_10)
                5 -> view.setImageResource(R.drawable.dice_d12_10)
            }
            11 -> when(type) {
                5 -> view.setImageResource(R.drawable.dice_d12_11)
            }
            12 -> when(type) {
                5 -> view.setImageResource(R.drawable.dice_d12_12)
            }
        }
    }

    fun countSuccesses(results : MutableList<Int>) : Int {
        var total = 0
        if(results.count() > 0)
            for(x in 0 until results.count())
                when(results[x]){
                    6 -> total++
                    7 -> total++
                    8 -> total+=2
                    9 -> total+=2
                    10 -> total+=3
                    11 -> total+=3
                    12 -> total+=4
                }

        return total
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


        binding.base6s.text = "Successes: " + arguments?.getInt(BASE6).toString() ?: throw IllegalStateException("No args provided")
        binding.base1s.text = "Banes: " + arguments?.getInt(BASE1).toString() ?: throw IllegalStateException("No args provided")
        binding.skill6s.text ="Successes: " +  arguments?.getInt(SKILL6).toString() ?: throw IllegalStateException("No args provided")
        //binding.skill1s.text ="Banes: " +  arguments?.getInt(SKILL1).toString() ?: throw IllegalStateException("No args provided")
        binding.gear6s.text ="Successes: " +  arguments?.getInt(GEAR6).toString() ?: throw IllegalStateException("No args provided")
        binding.gear1s.text ="Banes: " +  arguments?.getInt(GEAR1).toString() ?: throw IllegalStateException("No args provided")
        binding.other6s.text ="Successes: " +  arguments?.getInt(OTHER6).toString() ?: throw IllegalStateException("No args provided")
        binding.other1s.text ="Banes: " +  arguments?.getInt(OTHER1).toString() ?: throw IllegalStateException("No args provided")

        var totalsuc = 0
        var totalbane = 0
        totalsuc = (arguments?.getInt(BASE6) ?: 0) + (arguments?.getInt(SKILL6) ?: 0) + (arguments?.getInt(GEAR6) ?: 0)+ (arguments?.getInt(OTHER6) ?: 0)
        totalbane = (arguments?.getInt(BASE1) ?: 0) + (arguments?.getInt(GEAR1) ?: 0) + (arguments?.getInt(OTHER1) ?: 0)

        binding.totalSuccesses.text ="Total successes: "+ (totalsuc).toString() ?: throw IllegalStateException("No args provided")
        binding.totalBanes.text ="Total banes: "+ (totalbane).toString() ?: throw IllegalStateException("No args provided")

        arguments?.getBoolean(PUSHED)?.let{
            if(it) binding.titleText.text =getString( R.string.push_roll)
            else binding.titleText.text = getString(R.string.roll)
        }
        return binding.root
    }

    companion object {

        private const val BASE6 = "Base6"
        private const val BASE1 = "Base1"
        private const val SKILL6 = "Skill6"
        private const val SKILL1 = "Skill1"
        private const val GEAR6 = "Gear6"
        private const val GEAR1= "Gear1"
        private const val OTHER6 = "Other6"
        private const val OTHER1 = "Other1"

        private const val PUSHED = "Pushed"

        fun newInstance(
           base6 : Int, base1 : Int,
           skill6 : Int, skill1 : Int,
           gear6 : Int, gear1 : Int,
           other6 : Int, other1 : Int,
           pushed : Boolean
        ): ShowRoll = ShowRoll().apply {
            arguments = Bundle().apply {
                putInt(BASE6, base6)
                putInt(BASE1, base1)
                putInt(SKILL6, skill6)
                putInt(SKILL1, skill1)
                putInt(GEAR6, gear6)
                putInt(GEAR1, gear1)
                putInt(OTHER6, other6)
                putInt(OTHER1, other1)
                putBoolean(PUSHED, pushed)
            }
        }
    }
}
