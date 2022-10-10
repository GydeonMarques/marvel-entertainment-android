package br.com.gms.storage.sharedPrefs

import android.content.Context
import androidx.core.content.edit
import br.com.gms.storage.repository.LocalStorage
import javax.inject.Inject

class LocalSharedPreference @Inject constructor(context: Context) : LocalStorage {

    private val _preferencesName = "${context.packageName}.preferences"
    private val _sharedPreferences = context.getSharedPreferences(_preferencesName, Context.MODE_PRIVATE)

    companion object Key {
        const val FILTER_BY_GENRE = "FILTER_BY_GENRE"
    }

    override fun getString(key: String): String? {
        return _sharedPreferences.getString(key, null)
    }

    override fun putString(key: String, value: String) {
        _sharedPreferences.edit {
            putString(key, value)
            apply()
        }
    }

    override fun getInt(key: String): Int {
        return _sharedPreferences.getInt(key, -1)
    }

    override fun putInt(key: String, value: Int) {
        _sharedPreferences.edit {
            putInt(key, value)
            apply()
        }
    }

    override fun getBoolean(key: String): Boolean {
        return _sharedPreferences.getBoolean(key, false)
    }

    override fun putBoolean(key: String, value: Boolean) {
        _sharedPreferences.edit {
            putBoolean(key, value)
            apply()
        }
    }
}