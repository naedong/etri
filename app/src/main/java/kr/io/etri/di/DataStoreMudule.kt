package kr.io.etri.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-25(025)
 * Time: 오후 1:16
 */

//private const val DATA_STORE_NAME = "DATA_STORE_NAME" // 톰에서 생성할 예정
//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreMudule {
//    @Singleton
//    @Provides
//    fun providePreferencesDataStore(
//        @ApplicationContext context : Context
//    ) : DataStore<Preferences> = PreferenceDataStoreFactory.create {
//        context.preferencesDataStoreFile(DATA_STORE_NAME)
//    }
//}