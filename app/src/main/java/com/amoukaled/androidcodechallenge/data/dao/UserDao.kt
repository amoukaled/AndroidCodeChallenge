package com.amoukaled.androidcodechallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amoukaled.androidcodechallenge.data.entities.User

@Dao
interface UserDao {

    /**
     * Inserts a user in the User Table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    /**
     * Inserts all users in the User table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<User>)

    /**
     * Gets the row count for the user table.
     */
    @Query("SELECT COUNT(username) FROM user")
    suspend fun getRowCount(): Int

    /**
     * Gets the user by id.
     * @return The user or null if there's none.
     */
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun findByUsername(username: String): User?

}