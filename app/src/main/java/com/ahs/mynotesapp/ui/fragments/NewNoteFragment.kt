package com.ahs.mynotesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ahs.mynotesapp.R
import com.ahs.mynotesapp.databinding.FragmentNewNoteBinding
import com.ahs.mynotesapp.room.model.Notes
import com.ahs.mynotesapp.ui.MainActivity
import com.ahs.mynotesapp.ui.MainViewModel


class NewNoteFragment : Fragment() {
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_note, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (context as MainActivity).mainViewModel


        binding.fabSave.setOnClickListener() {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show()
            } else if (description.isEmpty()) {
                Toast.makeText(context, "Please Enter Description", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addNotes(Notes(title = title, description = description))
                Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
            }

        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}