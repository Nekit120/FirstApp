package com.example.firstapp.db

import androidx.lifecycle.*
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShoppingListNames
import kotlinx.coroutines.launch

class MainViewModel(database:MainDataBase) : ViewModel() {
    val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllItems().asLiveData()
    val allShoppingListNames: LiveData<List<ShoppingListNames>> = dao.getAllShopListNames().asLiveData()

    fun insertNote(note:NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListName(listName: ShoppingListNames) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    fun deleteNote(id:Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteShopListName(id:Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
    }
    fun updateNote(note:NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }
    fun updateListName(ShopListName:ShoppingListNames) = viewModelScope.launch {
        dao.updateListName(ShopListName)
    }


    class MainViewModelFactory(val database: MainDataBase): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }

    }
}