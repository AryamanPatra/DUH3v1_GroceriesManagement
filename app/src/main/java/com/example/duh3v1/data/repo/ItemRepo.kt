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

@OptIn(DelicateCoroutinesApi::class)
public class ItemRepo(application: Application) {
    private lateinit var itemDao: ItemDao
    private lateinit var itemList: LiveData<List<Item>>

    init {
        val appDataBase = AppDatabase.getInstance(application)
        if (appDataBase != null) {
            itemDao = appDataBase.itemDao()
            itemList = runBlocking {
                GlobalScope.async {
                    itemDao.getItems()
                }.await()
            }
        }
    }

    public suspend fun upsertData(item: Item): Int{
        return itemDao.upsertItem(item)
    }
    public suspend fun deleteData(item: Item){
        itemDao.deleteItem(item)
    }
    public suspend fun getAllData(): LiveData<List<Item>>{
        return itemList
    }
    public suspend fun getAllDataInOrder(sortOrder: SortOrder): LiveData<List<Item>>{
        when (sortOrder){
            SortOrder.NAME_ASC -> return itemDao.getItemsByNameAscending()
            SortOrder.NAME_DESC -> return itemDao.getItemsByNameDescending()
            SortOrder.QUANTITY_ASC -> return itemDao.getItemsByQuantityAscending()
            SortOrder.QUANTITY_DESC -> return itemDao.getItemsByQuantityDescending()
            SortOrder.CATEGORY_ASC -> return itemDao.getItemsByCategoryAscending()
            SortOrder.CATEGORY_DESC -> return itemDao.getItemsByCategoryDescending()
        }
    }
}