package com.ahs.mynotesapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ahs.mynotesapp.repository.NotesRepository
import com.ahs.mynotesapp.room.AppDatabase
import com.ahs.mynotesapp.room.model.Notes
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NotesRepository) : ViewModel() {

    fun addNotes(notes: Notes) {
        viewModelScope.launch {
            repository.insert(notes = notes)
        }
    }

    fun updateNotes(notes: Notes) {
        viewModelScope.launch {
            repository.update(notes = notes)
        }
    }

    fun deleteNotes(notes: Notes) {
        viewModelScope.launch {
            repository.delete(notes = notes)
        }
    }

    fun getAllNotes(): LiveData<List<Notes>> = repository.getNotes()
}

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            val taskDao = AppDatabase.getDatabase(context).taskDao()
            val repository = NotesRepository(taskDao)
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}