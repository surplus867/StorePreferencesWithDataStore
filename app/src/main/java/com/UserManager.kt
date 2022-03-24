package com

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {


    //create the datastore and give it name same as shared preferences
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("users")
    private val mDataStore: DataStore<Preferences> = context.datastore

    //create some keys that we will use to store and retrieve data
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
    }

    /**
     * Store user data
     * refer to the data store and using edit
     * we can store values using the keys
     */


    suspend fun storeUserData(age: Int, name: String) {
        mDataStore.edit { preferences ->

            preferences[USER_NAME_KEY] = name
            preferences[USER_AGE_KEY] = age

            // those are the preferences that we are editing
        }
    }

        //Create an age flow to retrieve age from the preferences
        // Flow is a part of kotlin coroutine

        val userAgeFlow: Flow<Int> = mDataStore.data.map {
            it[USER_AGE_KEY] ?: 0

            //zero is default value
        }

        //Create a name flow to retrieve name from the preferences
        val usernameFlow: Flow<String> = mDataStore.data.map {
            it[USER_NAME_KEY] ?: ""

            //empty string is a default value, required by default
        }
    }