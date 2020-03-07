package com.robertferreira.forbiddenlandscharcreator.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Kin
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import java.time.Instant


class Converters {

    @TypeConverter
    fun fromStringToSkillMap(value: String?): MutableMap<Skills, Int> {
        val mapType = object :
            TypeToken<MutableMap<Skills, Int>>() {}.type
        val result : MutableMap<Skills,Int> = Gson().fromJson(value, mapType)
        return result
    }
    @TypeConverter
    fun fromSkillMapToString(map: MutableMap<Skills, Int>?): String {
        val gson = Gson()
        return gson.toJson(map).toString()
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
        return gson.toJson(map).toString()
    }

    @TypeConverter
    fun fromStringToGears(value: String?): ArrayList<Gear> {
        val mapType = object :
            TypeToken<ArrayList<Gear>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromGearsToString(map: ArrayList<Gear>?): String {
        val gson = Gson()
        return gson.toJson(map).toString()
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
        return gson.toJson(map).toString()
    }

    @TypeConverter
    fun fromStringToGear(value: String?): Gear {
        val mapType = object :
            TypeToken<Gear?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromGearToString(map: Gear?): String {
        val gson = Gson()
        return gson.toJson(map).toString()
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
        return gson.toJson(map).toString()
    }

    @TypeConverter
    fun toMutableMapSS(value: String?): MutableMap<String,String> {
        val mapType = object :
            TypeToken<MutableMap<String,String>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromMutableMapSS(map: MutableMap<String,String>?): String {
        val gson = Gson()
        return gson.toJson(map).toString()
    }

    @TypeConverter
    fun toMutableMapIS(value: String?): MutableMap<Int,String> {
        val mapType = object :
            TypeToken<MutableMap<Int,String>?>() {}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromMutableMapIS(map: MutableMap<Int,String>?): String {
        val gson = Gson()
        return gson.toJson(map).toString()
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
