package com.amoukaled.androidcodechallenge.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = User.USER_TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = USERNAME_COLUMN_NAME)
    val username: String,

    @ColumnInfo(name = NAME_COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = PASSWORD_COLUMN_NAME)
    val password: String,
) {
    companion object {
        // static names technique to help in referencing foreign keys
        const val USER_TABLE_NAME = "user"
        const val USERNAME_COLUMN_NAME = "username"
        const val NAME_COLUMN_NAME = "name"
        const val PASSWORD_COLUMN_NAME = "password"
    }
}
