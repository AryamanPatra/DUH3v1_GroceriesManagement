package com.example.duh3v1.data.source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.duh3v1.data.models.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM Item")
    fun getItems(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY name ASC")
    fun getItemsByNameAscending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY (`quantity left` + `quantity reserved` + `quantity used`) ASC")
    fun getItemsByQuantityAscending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY category ASC")
    fun getItemsByCategoryAscending(): LiveData<List<Item>>

    @Query("SELECT * FROM Item ORDER BY name DESC")
    fun getItemsByNameDescending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY (`quantity left` + `quantity reserved` + `quantity used`) DESC")
    fun getItemsByQuantityDescending(): LiveData<List<Item>>
    @Query("SELECT * FROM Item ORDER BY category DESC")
    fun getItemsByCategoryDescending(): LiveData<List<Item>>

}