package com.robertferreira.forbiddenlandscharcreator

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.models.Weapon
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

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }

    //function loads a list of Talents from json file
    fun <T> creatJSONList(context: Context, filename : String) : List<T>{
        val jsonFileString = Utils.getJsonDataFromAsset(context, filename)

        val gson = Gson()
        val listType = object : TypeToken<List<T>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }

}

enum class Attributes(val id : Int) {
    Strength(0),
    Agility(1),
    Wits(2),
    Empathy(3);
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
    AnimalHandling (15);
    companion object {
        fun returnPairList(): List<Pair<Skills, Int>> {
            var list: MutableList<Pair<Skills, Int>> = mutableListOf()
            enumValues<Skills>().forEach {
                list.add(Pair(it, 0))
            }
            return list
        }
    }
}

class AttributeConverter {
    @TypeConverter
    fun toAttribute(status: Int): Attributes? {
        return Attributes.valueOf(status.toString())
    }

    @TypeConverter
    fun toInteger(attr: Attributes): Int? {
        return attr.id
    }
}

class SkillConverter {
    @TypeConverter
    fun toSkill(status: Int): Skills? {
       return Skills.valueOf(status.toString())
    }

    @TypeConverter
    fun toInteger(skill: Skills): Int? {
        return skill.id
    }
}

