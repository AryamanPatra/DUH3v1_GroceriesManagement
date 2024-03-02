package com.example.duh3v1.data.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.source.AppDatabase
import com.example.duh3v1.data.source.ItemDao
import com.example.duh3v1.ui.states.SortOrder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

public class ItemRepo(application: Application) {
    private lateinit var itemDao: ItemDao
    private lateinit var itemList: LiveData<List<Item>>

    init {
        val appDataBase = AppDatabase.getInstance(application)
        if (appDataBase != null) {
            itemDao = appDataBase.itemDao()
            itemList = itemDao.getItems()
        }
    }

    public fun upsertData(item: Item): Long{
        return itemDao.upsertItem(item)
    }
    public fun deleteData(item: Item){
        itemDao.deleteItem(item)
    }
    public fun getAllData(): LiveData<List<Item>>{
        return itemList
    }
    public fun getAllDataInOrder(sortOrder: SortOrder): LiveData<List<Item>>{
        return when (sortOrder){
            SortOrder.NAME_ASC -> itemDao.getItemsByNameAscending()
            SortOrder.NAME_DESC -> itemDao.getItemsByNameDescending()
            SortOrder.QUANTITY_ASC -> itemDao.getItemsByQuantityAscending()
            SortOrder.QUANTITY_DESC -> itemDao.getItemsByQuantityDescending()
            SortOrder.CATEGORY_ASC -> itemDao.getItemsByCategoryAscending()
            SortOrder.CATEGORY_DESC -> itemDao.getItemsByCategoryDescending()
        }
    }
}