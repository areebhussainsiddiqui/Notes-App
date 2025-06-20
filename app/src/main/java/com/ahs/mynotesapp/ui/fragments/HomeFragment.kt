package com.ahs.mynotesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.ahs.mynotesapp.R
import com.ahs.mynotesapp.databinding.FragmentHomeBinding
import com.ahs.mynotesapp.ui.MainActivity
import com.ahs.mynotesapp.ui.MainViewModel
import com.ahs.mynotesapp.ui.adapters.NotesAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (context as MainActivity).mainViewModel

        val noteAdapter = NotesAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity.let { it ->
            viewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->

                noteAdapter.differ.submitList(notes)
                binding.recyclerView.visibility = if (notes.isEmpty()) View.GONE else View.VISIBLE
                binding.tvNotAvailable.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE

            }
        }



        binding.fabAdd.setOnClickListener() {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}