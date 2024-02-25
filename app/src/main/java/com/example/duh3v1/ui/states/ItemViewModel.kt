package com.example.duh3v1.ui.states

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.repo.ItemRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var itemRepo: ItemRepo
    private lateinit var itemList: LiveData<List<Item>>
    init {
        itemRepo = ItemRepo(application)
        itemList = runBlocking {
            GlobalScope.async {
                itemRepo.getAllData()
            }.await()
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    public fun upsert(item: Item): Int{
        return viewModelScope.async {
            return@async upsert(item)
        }.getCompleted()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    public fun delete(item: Item){
        viewModelScope.async {
            return@async upsert(item)
        }.getCompleted()
    }
    public fun getAllItems(): LiveData<List<Item>>{
        return itemList
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    public fun getAllItemsSorted(sortOrder: SortOrder): LiveData<List<Item>>{
        return viewModelScope.async {
            return@async itemRepo.getAllDataInOrder(sortOrder)
        }.getCompleted()
    }

}