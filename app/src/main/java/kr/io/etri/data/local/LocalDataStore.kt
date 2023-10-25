package kr.io.etri.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.io.etri.domain.model.LegalQAModel
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-25(025)
 * Time: 오후 1:17
 */
// 1안 제거
//class LocalDataStore @Inject constructor(
//    private val dataStore: DataStore<Preferences>
//) {
//    private object DataStoreKeys {
//        const val CHAT_DATA_KEY = "CHAT_DATA_KEY"
//        val chatDataKey = stringPreferencesKey(CHAT_DATA_KEY)
//    }
//    suspend fun setChatData(name: LegalQAModel) { dataStore.edit { it[DataStoreKeys.chatDataKey] = Json.encodeToString(name) } }
//
//    val getChatData : Flow<LegalQAModel> = dataStore.data.map { Json.decodeFromString(it[DataStoreKeys.chatDataKey] ?: "")  }
//
//}