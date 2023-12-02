package com.android.kotlinflow.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.kotlinflow.util.Constants

@Entity(tableName = Constants.ROOM_TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String
)