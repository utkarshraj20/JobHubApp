package com.example.careerlink.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.careerlink.data.models.JobData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookmarkManager( context: Context ) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("BookmarkPrefs" , Context.MODE_PRIVATE )
    private val gson = Gson()

    fun saveBookmark(job: JobData){
        val bookMarks = getBookmarks().toMutableSet()
        bookMarks.add(job)
        val json = gson.toJson(bookMarks)
        sharedPreferences.edit().putString("bookmarks", json).apply()
    }

    fun removeBookmark( job: JobData){
        val bookMarks = getBookmarks().toMutableSet()
        bookMarks.remove(job)
        val json = gson.toJson(bookMarks)
        sharedPreferences.edit().putString("bookmarks", json).apply()
    }

    fun getBookmarks(): Set<JobData>{
        val json = sharedPreferences.getString("bookmarks", null)
        return if( json != null ){
            val type = object : TypeToken<Set<JobData>>() {}.type
            gson.fromJson(json, type)
        }
        else{
            emptySet()
        }
    }

    fun isBookmark(job: JobData): Boolean{
        return getBookmarks().contains(job)
    }
}
