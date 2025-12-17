package com.alirsyad.app.data.session

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


@SuppressLint("CommitPrefEdits")
class Sessions constructor(
    val context: Context
) {
    companion object {
        const val keyAlias: String = "_androidx_security_master_key_"
        const val secretSharedPref: String = "secret_shared_prefs"

        /** Data **/
        const val token: String = "token"
        const val nis: String = "nis"
        const val name: String = "name"
        const val kelas: String = "kelas"
        const val tingkat: String = "tingkat"
        const val tingkatId: String = "tingkat_id"
        const val jenjang: String = "jenjang"
        const val isLogin: String = "isLogin"
        const val isIntro: String = "Intro"
        const val role: String = "role"
        const val isPengunjung: String = "is_pengunjung"
        const val photo: String = "photo"
        const val email: String = "email"
        const val status: String = "status"
        const val classPosition: String = "class_position"
        const val classId: String = "class_id"
        const val userId: String = "user_id"
        const val isTab: String = "is_tab"
    }

    private var editor: SharedPreferences.Editor? = null
    private val spec = KeyGenParameterSpec.Builder(
        keyAlias,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(256)
        .build()

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        secretSharedPref,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        editor = pref.edit()
    }

    fun batch() = pref

    fun putString(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun putInt(key: String, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun putLong(key: String, value: Long) {
        editor!!.putLong(key, value)
        editor!!.commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }

    fun getString(key: String): String {
        return pref.getString(key, "").toString()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return pref.getInt(key, defValue)
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun logOut() {
        editor!!.clear()
        editor!!.commit()
    }
}