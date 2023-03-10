package com.binar.groupmaker.data.room.dao

import androidx.room.*
import com.binar.groupmaker.constant.CommonConstant
import com.binar.groupmaker.data.room.entity.ResultData
import com.binar.groupmaker.model.ResultModel


@Dao
interface ResultDao {

    @Query("SELECT "+ CommonConstant.KEY_RESULT_NAME+","+CommonConstant.KEY_RESULT_GROUP_NAME+" FROM " + CommonConstant.DATABASE_TABLE_RESULT + " GROUP BY " + CommonConstant.KEY_RESULT_NAME)
    suspend fun getAllResult() : List<ResultModel>

    @Query("SELECT * FROM " + CommonConstant.DATABASE_TABLE_RESULT + " WHERE " + CommonConstant.KEY_RESULT_NAME + " == :id ORDER BY " + CommonConstant.KEY_RESULT_TEAMS )
    suspend fun getAllResultById(id: String) : List<ResultData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultData: ResultData) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultData: List<ResultData>)

    @Delete
    suspend fun deleteResult(resultData: ResultData) : Int

    @Update
    suspend fun updateResult(resultData: ResultData) : Int

}