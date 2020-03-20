package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowRelationshipBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.RelationshipRowBinding

class CharShowRelationshipFragment : Fragment() {
    private lateinit var binding : FragmentCharShowRelationshipBinding
    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph){
        val application = requireNotNull(this.activity).application
        val dataSource = CharactersDatabase.getInstance(application).charactersDatabaseDAO()
        CharShowViewModelFactory(dataSource, application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_char_show_relationship,
            container,
            false
        )

        binding.viewModel = viewModel

        val adapter = RelationListAdapter()
        binding.relationsList.adapter = adapter

        viewModel.character.observe(viewLifecycleOwner, Observer {
            it.Relationships?.let { r ->
                adapter.data = r
                adapter.notifyDataSetChanged()
            }
        })

        return binding.root
    }

}

class Relationship
class RelationListAdapter() : RecyclerView.Adapter<RelationViewHolder>(){

    var data = mutableMapOf<String,String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RelationViewHolder, position: Int) {
        val item = data.entries.elementAt(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationViewHolder {
        return RelationViewHolder.from(parent)
    }
}


class RelationViewHolder private constructor(val binding: RelationshipRowBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: MutableMap.MutableEntry<String,String>) {
        val res = itemView.context.resources

        binding.titleField.text = item.key
        binding.descriptionField.text = item.value
        binding.deleteButton.isVisible = false
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): RelationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RelationshipRowBinding.inflate(layoutInflater, parent, false)
            return RelationViewHolder(binding)
        }
    }
}
