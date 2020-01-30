package com.robertferreira.forbiddenlandscharcreator.ui.charcreation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.robertferreira.forbiddenlandscharcreator.R
import com.robertferreira.forbiddenlandscharcreator.ui.charlist.CharListViewModel
import androidx.fragment.app.Fragment
import android.widget.*

class CharCreationFragment : Fragment() {

    private lateinit var charCreationViewModel: CharCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charCreationViewModel =
            ViewModelProviders.of(this).get(CharCreationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_charcreation, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_gallery)
        charCreationViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        setSpinner(root.findViewById(R.id.kinSpinner));
        setSpinner(root.findViewById(R.id.professionSpinner));
        setSpinner(root.findViewById(R.id.ageSpinner));

        return root
    }

    fun setSpinner(spinner: Spinner) {
        val mySpinner:Spinner= spinner
        var adapter = ArrayAdapter<Spinner>(this.requireContext(), android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter=adapter
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

    }
}