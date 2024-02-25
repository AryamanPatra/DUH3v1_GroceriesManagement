package com.example.duh3v1.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.duh3v1.data.models.Item

@Database(
    entities = [Item::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        private var instance : AppDatabase? = null
        @Synchronized
        public fun getInstance(context: Context): AppDatabase? {
            if (instance == null)
                instance =
                    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AutoGroceries")
                        .fallbackToDestructiveMigration().build()
            return instance
        }
    }
}