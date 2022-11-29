package com.example.fmtel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.fmtel.Utils.userKey
import com.example.fmtel.model.Data
import com.google.gson.GsonBuilder

object SharedPrefManager {


    //Shared Preference field used to save and retrieve JSON string
    lateinit var preferences: SharedPreferences

    //Name of Shared Preference file
    private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

    /**
     * Call this first before retrieving or saving object.
     *
     * @param application Instance of application class
     */
    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    inline fun makeUserPersist(isData: Boolean, key: String) {
        // saving data
        preferences.edit().putBoolean(key, isData).apply()
    }
      fun getToken(): String {
          var token = ""
        val profileData = get(userKey) as Data?

          if(profileData != null){
              token = profileData.token.toString()
          }

        return "Bearer $token"
    }





    inline fun nukeAllData() {
        preferences.edit().clear().apply()
    }

}
