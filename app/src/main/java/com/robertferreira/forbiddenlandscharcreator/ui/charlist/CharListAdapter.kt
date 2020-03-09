package com.robertferreira.forbiddenlandscharcreator.ui.charlist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.R.layout
import com.robertferreira.forbiddenlandscharcreator.databinding.TextItemViewBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charlist.CharListAdapter.CharacterListener

class CharListAdapter(val clickListener: CharacterListener) : RecyclerView.Adapter<TextItemViewHolder>(){

    var data = listOf<FLCharacter>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener,item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        return TextItemViewHolder.from(parent)
    }

    class CharacterListener(val clickListener: (charId: Long) -> Unit) {
        fun onClick(character: FLCharacter) = clickListener(character.charId)
    }

}


class TextItemViewHolder private constructor(val binding: TextItemViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: CharacterListener, item: FLCharacter) {
        val res = itemView.context.resources

        binding.nameText.text = item.Name + " - " + Professions.findProfession(item.Profession)
        binding.character = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TextItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TextItemViewBinding.inflate(layoutInflater, parent, false)
            return TextItemViewHolder(binding)
        }
    }
}