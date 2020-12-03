package com.udemy.roommodeldemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscribers")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String?,
    var email: String?
)
