package com.binar.groupmaker.di

import android.content.Context


import com.binar.groupmaker.data.pref.Preference
import com.binar.groupmaker.data.pref.PreferenceDataSource
import com.binar.groupmaker.data.pref.PreferenceDataSourceImpl
import com.binar.groupmaker.data.repository.LocalRepository
import com.binar.groupmaker.data.repository.LocalRepositoryImpl
import com.binar.groupmaker.data.room.AppDatabase
import com.binar.groupmaker.data.room.dao.GroupDao
import com.binar.groupmaker.data.room.dao.MemberDao
import com.binar.groupmaker.data.room.dao.ResultDao
import com.binar.groupmaker.data.room.datasource.*


object ServiceLocator {

    fun provideUserPreference(context: Context): Preference {
        return Preference(context)
    }

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideMemberDao(context: Context): MemberDao {
        return provideAppDatabase(context).memberDao()
    }


    fun provideGroupDao(context: Context): GroupDao {
        return provideAppDatabase(context).groupDao()
    }

    fun provideResultDao(context: Context): ResultDao {
        return provideAppDatabase(context).resultDao()
    }

    fun provideMemberDataSource(context: Context): MemberDataSource {
        return MemberDataSourceImpl(provideMemberDao(context))
    }


    fun provideGroupDataSource(context: Context): GroupDataSource {
        return GroupDataSourceImpl(provideGroupDao(context))
    }


    fun provideResultDataSource(context: Context): ResultDataSource {
        return ResultDataSourceImpl(provideResultDao(context))
    }


    fun providePreferenceDataSource(context: Context): PreferenceDataSource {
        return PreferenceDataSourceImpl(provideUserPreference(context))
    }

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            providePreferenceDataSource(context),
            provideMemberDataSource(context),
            provideGroupDataSource(context),
            provideResultDataSource(context)
        )
    }


}