package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.TalentsRowItemViewBinding

class TalentsListAdapter(val clickListener: TalentListener, val removeListener : RemoveListener)  : RecyclerView.Adapter<TextItemViewHolder>(){

    var data = listOf<Talent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener, removeListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        return TextItemViewHolder.from(parent)
    }

    class TalentListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }


    class RemoveListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }
}


class TextItemViewHolder private constructor(val binding: TalentsRowItemViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: TalentsListAdapter.TalentListener, removeListener: TalentsListAdapter.RemoveListener, item: Talent) {
        val res = itemView.context.resources

        binding.nameText.text = item.name
        binding.talent = item
        binding.clickListener = clickListener
        binding.removeListener = removeListener

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TextItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TalentsRowItemViewBinding.inflate(layoutInflater, parent, false)
            return TextItemViewHolder(binding)
        }
    }
}