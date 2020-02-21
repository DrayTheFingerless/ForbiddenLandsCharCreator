package com.robertferreira.forbiddenlandscharcreator

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

enum class Attributes(val id : Int) {
    Strength(0),
    Agility(1),
    Wits(2),
    Empathy(3)
}

enum class Skills(val id : Int) {
    Might (0),
    Endurance (1),
    Melee (2),
    Crafting (3),
    Stealth (4),
    SleightOfHand (5),
    Move (6),
    Marksmanship (7),
    Scouting (8),
    Lore (9),
    Survival (10),
    Insight (11),
    Manipulation (12),
    Performance (13),
    Healing (14),
    AnimalHandling (15),
}
