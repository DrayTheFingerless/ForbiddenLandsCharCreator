package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentTalentSelectListBinding
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.MyTalentSelectRecyclerViewAdapter.TalentSelectListener

import com.robertferreira.forbiddenlandscharcreator.ui.customviews.dummy.DummyContent
import com.robertferreira.forbiddenlandscharcreator.ui.customviews.dummy.DummyContent.DummyItem

class TalentSelectFragment : Fragment() {

    private val viewModel : TalentSelectViewModel  by navGraphViewModels(R.id.char_creation_nav_graph)
    lateinit var binding : FragmentTalentSelectListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_talent_select_list,
            container,
            false
        )

        val adapter = MyTalentSelectRecyclerViewAdapter(TalentSelectListener { id ->
            viewModel.setTalent(id)
            findNavController().navigateUp()
        })

        binding.talentList.adapter = adapter

        viewModel.gTalents.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        return binding.root
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Talent?)
    }

}
