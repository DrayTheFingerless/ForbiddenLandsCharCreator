package com.robertferreira.forbiddenlandscharcreator.ui.charlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R

class CharListFragment : Fragment() {

    private lateinit var charListViewModel: CharListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charListViewModel =
                ViewModelProviders.of(this).get(CharListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charlist, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        charListViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}