package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentTalentSelectBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.MyTalentSelectRecyclerViewAdapter.TalentSelectListener

import com.robertferreira.forbiddenlandscharcreator.ui.customviews.TalentSelectFragment.OnListFragmentInteractionListener
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_talent_select.view.*


class MyTalentSelectRecyclerViewAdapter(val clickListener: TalentSelectListener) : RecyclerView.Adapter<TalentSelectViewHolder>() {

    var data = listOf<Talent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TalentSelectViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener,item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentSelectViewHolder {
        return TalentSelectViewHolder.from(parent)
    }

    class TalentSelectListener(val clickListener: (tId: Int) -> Unit) {
        fun onClick(talent: Talent) = clickListener(talent.id)
    }
}

class TalentSelectViewHolder private constructor(val binding: FragmentTalentSelectBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: TalentSelectListener, item: Talent) {
        val res = itemView.context.resources

        binding.itemNumber.text = item.name
        binding.content.text = item.description
        binding.talent = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TalentSelectViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentTalentSelectBinding.inflate(layoutInflater, parent, false)
            return TalentSelectViewHolder(binding)
        }
    }
}
