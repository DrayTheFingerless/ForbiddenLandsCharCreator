package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.icu.text.IDNA.Info
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.AddTitleDescriptionDialogBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.DiceRollDialogBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.InfoCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.SkillCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.StepperRow
import com.robertferreira.forbiddenlandscharcreator.ui.diceroller.ShowRoll
import kotlinx.android.synthetic.main.attribute_stepper.view.*
import org.w3c.dom.Text

class InfoCreationFragment : Fragment() {

    companion object {
        fun newInstance() = InfoCreationFragment()
    }
    private lateinit var binding : InfoCreationFragmentBinding

    private val viewModel: CharCreationViewModel by navGraphViewModels(R.id.char_creation_nav_graph)
    lateinit var pop :ShowAddRelation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.info_creation_fragment,
            container,
            false
        )
        pop = ShowAddRelation()


        viewModel.char.observe(viewLifecycleOwner, Observer{
            it.Relationships?.let{r ->
                //LEARN RECYCLER VIEW BINDING
                for(item in r) {
                    binding.relationsLayout
                }
            }
        })
        binding.addRelationButton.setOnClickListener {
            val dialog = ShowAddRelation()
            dialog.show(childFragmentManager, "dialog")
        }

        return binding.root
    }

    fun setRelationshipList(){

        binding.addRelationButton.setOnClickListener {
            var iv = TextView(context)
            iv.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            // Creating a LinearLayout.LayoutParams object for text view
            val param = iv.layoutParams as LinearLayout.LayoutParams
            param.setMargins(8,8,8,8)
            param.weight = 1F
            iv.layoutParams = param
        }

    }
}

class ShowAddRelation : DialogFragment() {
    lateinit var binding : AddTitleDescriptionDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_title_description_dialog, container, false)

        return binding.root
    }

}
