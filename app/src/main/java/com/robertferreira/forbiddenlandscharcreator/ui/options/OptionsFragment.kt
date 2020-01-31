package com.robertferreira.forbiddenlandscharcreator.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R

class OptionsFragment : Fragment() {

    private lateinit var sendViewModel: OptionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
                ViewModelProviders.of(this).get(OptionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_options, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        sendViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}