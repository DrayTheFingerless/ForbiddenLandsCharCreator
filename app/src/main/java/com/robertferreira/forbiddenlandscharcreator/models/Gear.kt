package com.robertferreira.forbiddenlandscharcreator.models


open class Gear(
    val Id: Int,
    val Name: String,
    val Weight: Float,
    val Bonus: Int,
    val BonusType: BonusType) {
    val Equipped: Boolean = false
    val Selected: Boolean = false
    val Comment: String = ""
    //in Copper
    val Cost: Int = 0
    val Features: List<String> = listOf()
    val SkillMod : Int = 0
    val AttributeMod: Int = 0
    val ActionMod: Int = 0
}


class Weapon(id: Int,
             name: String,
             weight: Float,
             bonus: Int,
             bonusType: BonusType,
             type: ItemType = ItemType.Weapon,
             val TwoHanded: Boolean,
             val Damage: Int,
             val Range: Range,
             features : List<String>
             ) : Gear(id,name, weight,bonus,bonusType){

}

class Armor(id: Int,
            name: String,
            weight: Float,
            bonus: Int,
            bonusType: BonusType,
            Type: ItemType = ItemType.Armor,
            val ArmorRating: Int) : Gear(id,name, weight,bonus,bonusType) {
}


class Helmet(id: Int,
            name: String,
            weight: Float,
            bonus: Int,
            bonusType: BonusType,
            Type: ItemType = ItemType.Helmet,
            val ArmorRating: Int) : Gear(id,name, weight,bonus,bonusType) {
}


class Shield(id: Int,
            name: String,
            weight: Float,
            bonus: Int,
            bonusType: BonusType,
            Type: ItemType = ItemType.Shield,
            val ArmorRating: Int) : Gear(id,name, weight,bonus,bonusType) {
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
    Other(4)
}
