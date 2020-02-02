package com.robertferreira.forbiddenlandscharcreator

 class FLCharacter {
     var Name: String = ""
     var KinId: Int = -1
     var ProfessionId: Int = -1
     var AgeId: Int = -1
     var AgeNumber: Int = 0

     var StrengthNumber: Int = 0
     var AgilityNumber: Int = 0
     var WitsNumber: Int = 0
     var Empathy: Int = 0

     var Pride: String = ""
     var DarkSecret: String = ""
     var Reputation: Int = 0

     var Might: Int = 0
     var Endurance: Int = 0
     var Melee: Int = 0
     var Crafting: Int = 0
     var Stealth: Int = 0
     var SleightOfHand: Int = 0
     var Move: Int = 0
     var Marksmanship: Int = 0
     var Scouting: Int = 0
     var Lore: Int = 0
     var Survivar: Int = 0
     var Insight: Int = 0
     var Manipulation: Int = 0
     var Performance: Int = 0
     var Healing: Int = 0
     var AnimalHandling: Int = 0

     var TalentList: ArrayList<Talent> = ArrayList()
     var Face: String = ""
     var Body: String = ""
     var Clothing: String = ""

     var CurrentSkillPoints : Int = 0
     var TotalSkillPoints : Int = 0

     var CurrentWillPoints : Int = 0

     var Relationships: HashMap<String, String> = HashMap()

     var CarryCapacity: Int = 0
     var Gear: HashMap<Int, String> = HashMap()

     var FoodDie: Int = 0
     var WaterDie: Int = 0
     var ArrowsDie: Int = 0
     var TorchesDie: Int = 0

     var Money: Int = 0
 }