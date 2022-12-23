package com.binar.groupmaker.data.room.dao

import androidx.room.*


import com.binar.groupmaker.constant.CommonConstant
import com.binar.groupmaker.data.room.entity.Group

@Dao
interface GroupDao {

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE_GROUP + " ORDER BY " + CommonConstant.KEY_ROWID + " DESC")
    suspend fun getAllGroup() : List<Group>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: List<Group>)

    @Delete
    suspend fun deleteGroup(group: Group) : Int

    @Update
    suspend fun updateGroup(group: Group) : Int

}