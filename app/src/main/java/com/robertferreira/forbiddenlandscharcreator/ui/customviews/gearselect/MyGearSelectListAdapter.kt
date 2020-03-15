package com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentTalentSelectBinding
import com.robertferreira.forbiddenlandscharcreator.databinding.GearSelectRowBinding
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.models.Weight
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect.MyGearSelectListAdapter.GearSelectListener
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_talent_select.view.*


class MyGearSelectListAdapter(val clickListener: GearSelectListener) : RecyclerView.Adapter<GearSelectViewHolder>() {

    var data = listOf<Gear>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GearSelectViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener,item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearSelectViewHolder {
        return GearSelectViewHolder.from(parent)
    }

    class GearSelectListener(val clickListener: (gId: Int) -> Unit) {
        fun onClick(gear: Gear) = clickListener(gear.id)
    }
}

class GearSelectViewHolder private constructor(val binding: GearSelectRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: GearSelectListener, item: Gear) {
        val res = itemView.context.resources

        binding.itemName.text = item.name
        binding.weight.text = item.weight.toString()
        binding.gear = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GearSelectViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GearSelectRowBinding.inflate(layoutInflater, parent, false)
            return GearSelectViewHolder(binding)
        }
    }
}
