package com.robertferreira.forbiddenlandscharcreator.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Kin
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import java.time.Instant


class Converters {

    @TypeConverter
    fun fromStringToSkillMap(value: String?): MutableMap<Skills, Int> {
        val mapType = object :
            TypeToken<MutableMap<Skills, Int>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromSkillMapToString(map: MutableMap<Skills, Int>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromStringToTalents(value: String?): ArrayList<Talent> {
        val mapType = object :
            TypeToken<ArrayList<Talent>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromTalentsToString(map: ArrayList<Talent>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromStringToTalent(value: String?): Talent {
        val mapType = object :
            TypeToken<Talent?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromTalentToString(map: Talent?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromStringToSkill(value: String?): Skills {
        val mapType = object :
            TypeToken<Skills?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromSkillToString(map: Skills?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun toHashMapSS(value: String?): HashMap<String,String> {
        val mapType = object :
            TypeToken<HashMap<String,String>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromHashMapSS(map: HashMap<String,String>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun toHashMapIS(value: String?): HashMap<Int,String> {
        val mapType = object :
            TypeToken<HashMap<Int,String>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromHashMapIS(map: HashMap<Int,String>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromInstant(value: Instant): Long {
        return value.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long): Instant {
        return Instant.ofEpochMilli(value)
    }
}
