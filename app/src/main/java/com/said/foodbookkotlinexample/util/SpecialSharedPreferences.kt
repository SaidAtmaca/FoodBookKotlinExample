package com.said.foodbookkotlinexample.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SpecialSharedPreferences {

    companion object{
        private val TIME="time"
        private var sharedPreferences: SharedPreferences?= null

        @Volatile private var instance: SpecialSharedPreferences?=null
        private val lock= Any()
        operator fun invoke(context: Context):SpecialSharedPreferences= instance ?: synchronized(lock){
                instance ?: makeSpecialSharedPreferences(context).also {
                    instance=it
                }
        }

        private fun makeSpecialSharedPreferences(context: Context):SpecialSharedPreferences{
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }

    }

    fun saveTime(time: Long){
        sharedPreferences?.edit(commit=true){

            putLong(TIME,time)
        }
    }

    fun takeTime() = sharedPreferences?.getLong(TIME,0)
}