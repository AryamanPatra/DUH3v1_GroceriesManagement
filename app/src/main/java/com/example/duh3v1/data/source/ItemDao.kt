package com.example.duh3v1.data.source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.duh3v1.data.models.Item

@Dao
interface ItemDao {
    @Upsert
    suspend fun upsertItem(item: Item): Int

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM Item")
    suspend fun getItems(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY name ASC")
    suspend fun getItemsByNameAscending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY sum(`quantity left`,`quantity reserved`,`quantity used`) ASC")
    suspend fun getItemsByQuantityAscending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY category ASC")
    suspend fun getItemsByCategoryAscending(): LiveData<List<Item>>

    @Query("SELECT * FROM Item ORDER BY name DESC")
    suspend fun getItemsByNameDescending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY sum(`quantity left`,`quantity reserved`,`quantity used`) DESC")
    suspend fun getItemsByQuantityDescending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY category DESC")
    suspend fun getItemsByCategoryDescending(): LiveData<List<Item>>

}