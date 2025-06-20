package com.ahs.mynotesapp.repository

import androidx.lifecycle.LiveData
import com.ahs.mynotesapp.room.dao.NotesDao
import com.ahs.mynotesapp.room.model.Notes

class NotesRepository(private val dao: NotesDao) {

    suspend fun insert(notes: Notes) = dao.insert(notes)
    suspend fun update(notes: Notes) = dao.update(notes)
    suspend fun delete(notes: Notes) = dao.delete(notes)

    fun getNotes(): LiveData<List<Notes>> = dao.getNotes()
}