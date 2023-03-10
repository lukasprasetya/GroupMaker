package com.binar.groupmaker.data.room.datasource


import com.binar.groupmaker.data.room.dao.GroupDao
import com.binar.groupmaker.data.room.entity.Group


interface GroupDataSource {
    suspend fun getAllGroup(): List<Group>



    suspend fun insertGroup(group: Group): Long

    suspend fun deleteGroup(group: Group): Int

    suspend fun updateGroup(group: Group): Int
}

class GroupDataSourceImpl(private val dao: GroupDao) : GroupDataSource {
    override suspend fun getAllGroup(): List<Group> {
        return dao.getAllGroup()
    }




    override suspend fun insertGroup(group: Group): Long {
        return dao.insertGroup(group)
    }

    override suspend fun deleteGroup(group: Group): Int {
        return dao.deleteGroup(group)
    }

    override suspend fun updateGroup(group: Group): Int {
        return dao.updateGroup(group)
    }


}