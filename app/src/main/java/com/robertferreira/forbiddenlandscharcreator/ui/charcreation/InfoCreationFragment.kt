package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Activity
import android.content.Intent
import android.graphics.fonts.Font
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.AddTitleDescriptionDialogBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.InfoCreationFragmentBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.RelationshipRow
import kotlinx.android.synthetic.main.add_title_description_dialog.view.*
import kotlinx.android.synthetic.main.add_title_description_dialog.view.description_field
import kotlinx.android.synthetic.main.dice_roll_dialog.view.*
import kotlinx.android.synthetic.main.relationship_row.*
import kotlinx.android.synthetic.main.relationship_row.view.*
import org.w3c.dom.Text


class InfoCreationFragment : Fragment() {
    val REQUEST_CODE = 11
    val RESULT_CODE = 12

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

        binding.charViewModel = viewModel


        viewModel.char.observe(viewLifecycleOwner, Observer{
            it.Relationships?.let{r ->
                //LEARN RECYCLER VIEW BINDING
                for(item in r) {
                    binding.relationsLayout
                }
            }
        })
        binding.addRelationButton.setOnClickListener {
            fragmentManager?.let {
                val dialog = ShowAddRelation()
                dialog.setTargetFragment(this, REQUEST_CODE)
                dialog.show(it, "dialog")
            }
        }

        setRelationshipList()

        binding.nextInfoButton.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_skills_to_info))


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.extras?.let { ex ->
                    for(item in ex.keySet())
                        viewModel.addRelationship(item, ex.getString(item) ?: "")
                }

            }
        }
    }

    fun setRelationshipList(){

        viewModel.char.observe(viewLifecycleOwner, Observer {
            binding.relationsLayout.removeAllViews()

            for (item in it.Relationships) {
                context?.let { con ->
                    var row = RelationshipRow(con)
                    row.title_field.text = item.key

                    row.setValues(item.key,item.value)
                    // Creating a LinearLayout.LayoutParams object for text view

                    binding.relationsLayout.addView(row)
                }
            }
        })
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

        binding.addButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(binding.nameField.text.toString(), binding.descriptionField.text.toString())

            val intent = Intent().putExtras(bundle)

            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

            dismiss()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    }

}
