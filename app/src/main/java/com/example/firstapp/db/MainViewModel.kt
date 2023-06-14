package com.example.firstapp.db

import androidx.lifecycle.*
import com.example.firstapp.entities.LibraryItem
import com.example.firstapp.entities.NoteItem
import com.example.firstapp.entities.ShopListItem
import com.example.firstapp.entities.ShopListNameItem
import kotlinx.coroutines.launch

class MainViewModel(database:MainDataBase) : ViewModel() {
    val dao = database.getDao()
    val libraryItems = MutableLiveData<List<LibraryItem>>()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllItems().asLiveData()
    val allShopListNameItem: LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()

    fun getAllItemsFromList(listId: Int):LiveData<List<ShopListItem>>{
        return dao.getAllShopListItems(listId).asLiveData()
    }
    fun getAllLibraryItems (name: String) = viewModelScope.launch {
        libraryItems.postValue(dao.getAllLibraryItems(name))
    }

    fun insertNote(note:NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }
    fun insertShopListItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
        if(!isLibraryItemExists(shopListItem.name)) dao.insertLibraryItem(LibraryItem(null,shopListItem.name))
    }
    fun deleteNote(id:Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }
    fun deleteShopList(id:Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
        dao.deleteShopItemsByListId(id)
    }
    fun deleteShopListFullItem(id:Int) = viewModelScope.launch {
        dao.deleteShopItemsByListId(id)
    }
    fun deleteLibraryItem(id:Int) = viewModelScope.launch {
        dao.deleteLibraryItem(id)
    }
    fun updateNote(note:NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }
    fun updateListName(ShopListName:ShopListNameItem) = viewModelScope.launch {
        dao.updateListName(ShopListName)
    }
    fun updateListItem(item:ShopListItem) = viewModelScope.launch {
        dao.updateListItem(item)
    }
    fun updateLibraryItem(item:LibraryItem) = viewModelScope.launch {
        dao.updateLibraryItem(item)
    }
    suspend fun isLibraryItemExists(name:String): Boolean {
        return dao.getAllLibraryItems(name).isNotEmpty()
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