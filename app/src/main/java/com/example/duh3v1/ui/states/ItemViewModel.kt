package com.example.duh3v1.ui.states

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.repo.ItemRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

public class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var itemRepo: ItemRepo
    private lateinit var itemList: LiveData<List<Item>>
    init {
        itemRepo = ItemRepo(application)
        itemList = liveData(Dispatchers.IO) {
            emitSource(itemRepo.getAllData())
        }

    }
    public fun upsert(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemRepo.upsertData(item)
        }
    }
    public fun delete(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemRepo.deleteData(item)
        }
    }
    public fun getAllItems(): LiveData<List<Item>>{
        return itemList
    }
    public fun getAllItemsSorted(sortOrder: SortOrder): LiveData<List<Item>>{
        return liveData(Dispatchers.IO) {
            val items = itemRepo.getAllDataInOrder(sortOrder)
            emitSource(items)
        }
    }

}