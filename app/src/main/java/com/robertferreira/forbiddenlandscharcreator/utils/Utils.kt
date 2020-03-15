package com.robertferreira.forbiddenlandscharcreator

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.models.Armour
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.models.Weapon
import java.io.IOException
import java.lang.reflect.Type


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
    fun loadGears(context: Context, filename : String) : List<Gear>{
        val jsonFileString = Utils.getJsonDataFromAsset(context, filename)

        val gson = Gson()
        val listType = object : TypeToken<List<Gear>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }

    //function loads a list from json file
    inline fun <reified T> creatJSONList(context: Context, filename : String, typeToken: Type) : T{
        val jsonFileString = Utils.getJsonDataFromAsset(context, filename)

        val gson = GsonBuilder().create()
        return gson.fromJson<T>(jsonFileString, typeToken)
    }

}

enum class Attributes(val id : Int) {
    Strength(0),
    Agility(1),
    Wits(2),
    Empathy(3);
}


enum class Skills(val id : Int) {
    @SerializedName("0")
    Might (0),
    @SerializedName("1")
    Endurance (1),
    @SerializedName("2")
    Melee (2),
    @SerializedName("3")
    Crafting (3),
    @SerializedName("4")
    Stealth (4),
    @SerializedName("5")
    SleightOfHand (5),
    @SerializedName("6")
    Move (6),
    @SerializedName("7")
    Marksmanship (7),
    @SerializedName("8")
    Scouting (8),
    @SerializedName("9")
    Lore (9),
    @SerializedName("10")
    Survival (10),
    @SerializedName("11")
    Insight (11),
    @SerializedName("12")
    Manipulation (12),
    @SerializedName("13")
    Performance (13),
    @SerializedName("14")
    Healing (14),
    @SerializedName("15")
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

