package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robertferreira.forbiddenlandscharcreator.databinding.GearItemRowViewBinding
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.GearListAdapter.GearListener
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.GearListAdapter.DeleteGearListener

class GearListAdapter(val clickListener: GearListener,
                         val removeListener : DeleteGearListener)  : RecyclerView.Adapter<GearRowViewHolder>(){

    var data = listOf<Gear>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GearRowViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener, removeListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearRowViewHolder {
        return GearRowViewHolder.from(parent)
    }

    class GearListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(gear: Gear) = clickListener(gear.Id)
    }


    class DeleteGearListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(gear: Gear) = clickListener(gear.Id)
    }
}


class GearRowViewHolder private constructor(val binding: GearItemRowViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: GearListener, deleteListener: DeleteGearListener, item: Gear) {
        val res = itemView.context.resources

        binding.nameText.text = item.Name
        binding.gear = item
        binding.clickListener = clickListener
        binding.deleteListener = deleteListener
        binding.weightText.text = item.Weight.toString()
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GearRowViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GearItemRowViewBinding.inflate(layoutInflater, parent, false)
            return GearRowViewHolder(binding)
        }
    }
}