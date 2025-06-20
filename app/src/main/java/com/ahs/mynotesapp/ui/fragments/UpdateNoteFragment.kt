package com.ahs.mynotesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ahs.mynotesapp.R
import com.ahs.mynotesapp.databinding.FragmentUpdateNoteBinding
import com.ahs.mynotesapp.room.model.Notes
import com.ahs.mynotesapp.ui.MainActivity
import com.ahs.mynotesapp.ui.MainViewModel


class UpdateNoteFragment : Fragment() {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var currentNote: Notes
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_note, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        viewModel = (context as MainActivity).mainViewModel
        currentNote = args.note!!
        binding.etTitle.setText(currentNote.title)
        binding.etDescription.setText(currentNote.description)

        binding.fabUpdate.setOnClickListener() {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(context, "Please Enter Title", Toast.LENGTH_SHORT).show()
            } else if (description.isEmpty()) {
                Toast.makeText(context, "Please Enter Description", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateNotes(
                    Notes(
                        id = currentNote.id,
                        title = title,
                        description = description
                    )
                )
                Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
        }

        binding.fabDelete.setOnClickListener {
            showConfirmationDialog()

        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Alert!")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete it!") { _, _ ->
                viewModel.deleteNotes(notes = currentNote)
                mView.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
                Toast.makeText(requireContext(),"Note Deleted",Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel", null)
        }.show()

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}