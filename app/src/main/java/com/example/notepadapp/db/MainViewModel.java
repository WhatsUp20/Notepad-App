package com.example.notepadapp.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static NotesDatabase database;
    private LiveData<List<Note>> notes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = NotesDatabase.getInstance(application);
        notes = database.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note) {
        new InsertNoteTask().execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteTask().execute(note);
    }

    public void deleteAllNotes() {
        new DeleleAllNotes().execute();
    }

    private static class InsertNoteTask extends AsyncTask<Note,Void,Void> {

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                database.notesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteNoteTask extends AsyncTask<Note,Void,Note> {

        @Override
        protected Note doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                database.notesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleleAllNotes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.notesDao().deleteAllNotes();
            return null;
        }
    }

}
