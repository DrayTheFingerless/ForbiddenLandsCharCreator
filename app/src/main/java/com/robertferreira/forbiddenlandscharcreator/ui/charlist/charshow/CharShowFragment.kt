package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.robertferreira.forbiddenlandscharcreator.FLCharacter

import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.databinding.FragmentCharShowBinding
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.CharViewModel


class CharShowFragment : Fragment() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var characterShowCollectionAdapter: CharacterShowAdapter
    private lateinit var viewPager: ViewPager2

    private val viewModel : CharShowViewModel by navGraphViewModels(R.id.char_show_nav_graph)

    lateinit var binding : FragmentCharShowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_char_show, container, false)
        //viewModel = ViewModelProviders.of(this).get(CharShowViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel

        arguments?.let {
            it.getParcelable<FLCharacter>("character")?.let { c ->
                viewModel.setCharacter(c)
            }
        }

        viewModel.isBroken.observe(viewLifecycleOwner, Observer {
            if(it) {
                Toast.makeText(context, "You're broken!", Toast.LENGTH_SHORT).show()
                binding.nameText.setTextColor(resources.getColor(R.color.colorAccent))
            }
            else{
                binding.nameText.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        characterShowCollectionAdapter = CharacterShowAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = characterShowCollectionAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 ->tab.text = "Main"
                1 -> tab.text = "Skills"
                2 -> tab.text = "Talents"
                3 -> tab.text = "Info"
                4 -> tab.text = "Gear"
            }
        }.attach()


    }
}


const val ARG_PAGE = "page"

class CharacterShowAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    lateinit var fragment : Fragment
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        when(position){
            0 -> fragment = CharShowMainFragment()
            1 -> fragment = CharShowSkillsFragment()
            2 -> fragment = CharShowTalentFragment()
            3 -> fragment = CharShowInfoFragment()
            4 -> fragment = CharShowGearFragment()
            else -> fragment = CharShowMainFragment()
        }

        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_PAGE, position + 1)
        }
        return fragment
    }
}
