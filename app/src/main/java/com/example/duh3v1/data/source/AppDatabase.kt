package com.example.duh3v1.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.duh3v1.data.models.Item
import com.example.duh3v1.data.models.RefImageConverter

@TypeConverters(RefImageConverter::class)
@Database(
    entities = [Item::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile
        private var instance : AppDatabase? = null

        public fun getInstance(context: Context): AppDatabase? {
            if (instance!=null)
                return instance
            else{
                synchronized(this){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AutoGroceries"
                    ).fallbackToDestructiveMigration()
                        .build()
                    return instance
                }
            }
        }
    }
}