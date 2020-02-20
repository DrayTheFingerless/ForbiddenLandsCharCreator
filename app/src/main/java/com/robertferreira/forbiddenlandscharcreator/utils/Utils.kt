package com.robertferreira.forbiddenlandscharcreator.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Talent
import java.io.IOException


object Utils {

    //get json from asset using GSON
    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    //function loads a list of Talents from json file
    fun loadTalents(context: Context, filename : String) : List<Talent>{
        val jsonFileString = Utils.getJsonDataFromAsset(context, filename)
        Log.i("talents", jsonFileString)

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }

}