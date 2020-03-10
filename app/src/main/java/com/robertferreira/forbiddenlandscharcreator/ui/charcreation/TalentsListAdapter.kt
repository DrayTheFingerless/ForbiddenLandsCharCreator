package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.TalentsRowItemViewBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.TalentsListAdapter.TalentListener
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.TalentsListAdapter.RemoveListener
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.TalentsListAdapter.AddListener

class TalentsListAdapter(val clickListener: TalentListener,
                         val removeListener : RemoveListener,
                         val addListener : AddListener,
                         val showOnly : Boolean)  : RecyclerView.Adapter<TalentRowViewHolder>(){

    var data = listOf<Talent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TalentRowViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener, removeListener,addListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentRowViewHolder {
        return TalentRowViewHolder.from(parent, showOnly)
    }

    class TalentListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }


    class RemoveListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }
    class AddListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }
}


class TalentRowViewHolder private constructor(val binding: TalentsRowItemViewBinding,val showOnly: Boolean): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: TalentListener, removeListener: RemoveListener,addListener:AddListener, item: Talent) {
        val res = itemView.context.resources

        binding.nameText.text = item.name
        binding.talent = item
        binding.clickListener = clickListener
        binding.removeListener = removeListener
        binding.addListener = addListener
        binding.rankValue.text = item.rankValue.toString()
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, showOnly : Boolean): TalentRowViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TalentsRowItemViewBinding.inflate(layoutInflater, parent, false)
            return TalentRowViewHolder(binding, showOnly)
        }
    }
}