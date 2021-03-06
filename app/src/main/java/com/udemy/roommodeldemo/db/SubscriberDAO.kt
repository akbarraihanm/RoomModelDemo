package com.udemy.roommodeldemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insert(subscriber: Subscriber): Long

    @Update
    suspend fun update(subscriber: Subscriber): Int

    @Delete
    suspend fun delete(subscriber: Subscriber): Int

    @Query("DELETE FROM subscribers")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM subscribers")
    fun getAllSubscribers(): LiveData<List<Subscriber>>

}