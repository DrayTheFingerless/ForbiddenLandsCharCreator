package com.robertferreira.forbiddenlandscharcreator.models

import android.content.Context
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Utils.creatJSONList


open class Gear(
    val Id: Int,
    val Name: String,
    val Weight: Weight,
    val Bonus: Int,
    val GearType: ItemType,
    val Features: List<String> = listOf()
) {
    val Equipped: Boolean = false
    val Selected: Boolean = false
    val Comment: String = ""
    //in Copper
    val Cost: Int = 0
    val SkillMod : Int = 0
    val AttributeMod: Int = 0
    val ActionMod: Int = 0
    val BonusType: Skills = Skills.Might
}


class Weapon(id: Int,
             name: String,
             weight: Weight,
             bonus: Int,
             bonusType: Skills,
             val TwoHanded: Boolean,
             val Damage: Int,
             val Range: Range,
             features : List<String>,
             gearType: ItemType = ItemType.Weapon
             ) : Gear(id,name, weight,bonus,gearType,features){

    companion object{
        fun getWeapons(context: Context) : List<Weapon>{
            return creatJSONList<Weapon>(context,"weapons_gear")
        }
    }
}

class Armour(id: Int,
            name: String,
            weight: Weight,
            bonus: Int,
            features : List<String>,
            gearType: ItemType = ItemType.Weapon
) : Gear(id,name, weight,bonus,gearType,features) {
    companion object{
        fun getArmours(context: Context) : List<Armour>{
            return creatJSONList<Armour>(context,"armor_gear")
        }
    }
}

class Shield(id: Int,
            name: String,
            weight: Weight,
            bonus: Int,
            features : List<String>,
            gearType: ItemType = ItemType.Shield) : Gear(id,name, weight,bonus,gearType,features) {
}


enum class BonusType(i:Int){
    Base(0),
    Skill(1),
    Gear(2),
    Other(3)
}

enum class Weight(i : Int){
   Tiny(0),
    Light(1),
    Medium(2),
    Heavy(3),
    Other(4)
}

enum class Range(i:Int){
    ArmsLength(0),
    Near(1),
    Short(2),
    Long(3)
}

enum class ItemType(i:Int){
    Armor(0),
    Helmet(1),
    Shield(2),
    Weapon(3),
    Ranged(4),
    Other(5)
}
