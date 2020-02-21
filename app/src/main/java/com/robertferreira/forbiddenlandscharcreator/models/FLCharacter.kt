package com.robertferreira.forbiddenlandscharcreator

import org.w3c.dom.Attr

class FLCharacter {


     var Name: String = ""
     var Kin: Kin = Kins.kins[0]
     var Profession: Profession = Professions.professions[0]

     //0 - Young, 1 - Adult, 2 - Old
     var AgeId: Int = 0
     var AgeNumber: Int = 0

     var AttributePoints : Int = 5
     var StrengthMax : Int = 4
     var Strength: Int = 2
     var AgilityMax : Int = 4
     var Agility: Int = 2
     var WitsMax : Int = 4
     var Wits: Int = 2
     var EmpathyMax : Int = 4
     var Empathy: Int = 2

     var Pride: String = ""
     var DarkSecret: String = ""
     var Reputation: Int = 0

     var CurrentSkillPoints : Int = 8
     var MySkills : MutableMap<Skills, Int> = mutableMapOf()


     var TalentList: ArrayList<Talent> = ArrayList()
     var Face: String = ""
     var Body: String = ""
     var Clothing: String = ""



     var CurrentWillPoints : Int = 0

     var Relationships: HashMap<String, String> = HashMap()

     var CarryCapacity: Int =  0

     var Gear: HashMap<Int, String> = HashMap()

     //0 = none, 1 = d6, 2 = d8, 3 = d10, 4 = d12
     var FoodDie: Int = 1
     var WaterDie: Int = 1
     var ArrowsDie: Int = 0
     var TorchesDie: Int = 0


     //in Silver
     var Money: Int = 0

     init {
         enumValues<Skills>().forEach { MySkills.set(it, 0)  }
     }


     fun UpdateKin(kin : Kin)
     {
         val previousKin = this.Kin
         this.Kin = kin
         UpdateKeyAttributes(previousKin.KeyAttribute, this.Kin.KeyAttribute)

     }

     fun UpdateProfession(profession : Profession)
     {
         val previousProfession = this.Profession
         this.Profession = profession
         UpdateKeyAttributes(previousProfession.KeyAttribute, this.Profession.KeyAttribute)

         //if any non key skill is above 1, update skill point pull and reset sklls to 1
         MySkills.forEach{
             if(!this.Profession.Skills.contains(it.key) && it.value > 1) {
                 CurrentSkillPoints = CurrentSkillPoints + (it.value - 1)
                 MySkills.put(it.key, 1)
            }
         }

         //insert Gear logic

     }

     fun UpdateAge(ageId : Int, ageNumber : Int)
     {
        //0 - Young, 1 - Adult, 2 - Old
         //Young: Attributes 15 , Skills 8, Talents 1, Reputation 0
         //Adult: Attributes 14, Skills 10, Talents 2, Reputation 1
         //Old: Attributes 13, Skills 12, Talents 3, Reputation 2
         var maxAttr = 15
         var maxSkill = 12
         var maxTalent = 3
         var maxRep = 2

         AgeId = ageId
         AgeNumber = ageNumber

         when(AgeId){
             0-> {
                 maxAttr = 15
                 maxSkill = 8
                 maxTalent = 1
                 maxRep = 0
             }
             1-> {
                 maxAttr = 14
                 maxSkill = 10
                 maxTalent = 2
                 maxRep = 1
             }
             2-> {
                 maxAttr = 13
                 maxSkill = 12
                 maxTalent = 3
                 maxRep = 2
             }
         }

         var currentTotalAttr = Strength + Agility + Wits + Empathy + AttributePoints
         var currentTotalSkills = MySkills.values.sum() + CurrentSkillPoints

         if(currentTotalAttr - maxAttr < AttributePoints)
         {    AttributePoints = AttributePoints - (currentTotalAttr - maxAttr)}
         else {
             AttributePoints = maxAttr - 8
             Strength = 2
             Agility = 2
             Wits = 2
             Empathy = 2
         }


         if(currentTotalSkills - maxSkill < CurrentSkillPoints)
            CurrentSkillPoints = CurrentSkillPoints - (currentTotalSkills - maxSkill)
         else {
            CurrentSkillPoints = maxSkill
            MySkills.forEach{MySkills.set(it.key, 0)}
         }
     }

     private fun UpdateKeyAttributes(previousAttribute : Attributes, attribute : Attributes){
         if(previousAttribute != attribute) {
             //check which attribute key was previous, then reduce value of previous key attribute, check if new max is higher than current value,
             //if current value higher than max, decrement current and add that point back to Attribute pool
             when (previousAttribute) {
                 Attributes.Strength -> {
                     StrengthMax--
                     if (StrengthMax < Strength) {
                         Strength--
                         AttributePoints++
                     }
                 }
                 Attributes.Agility -> {
                     AgilityMax--
                     if (AgilityMax < Agility) {
                         Agility--
                         AttributePoints++
                     }
                 }
                 Attributes.Wits -> {
                     WitsMax--
                     if (WitsMax < Wits) {
                         Wits--
                         AttributePoints++
                     }
                 }
                 Attributes.Empathy -> {
                     EmpathyMax--
                     if (EmpathyMax < Empathy) {
                         Empathy--
                         AttributePoints++
                     }
                 }
             }
             //increment new key attribute by 1, after decrementing previous key attribute
             when (attribute) {
                 Attributes.Strength -> StrengthMax++
                 Attributes.Agility -> AgilityMax++
                 Attributes.Wits -> WitsMax++
                 Attributes.Empathy -> EmpathyMax++
             }
         }
     }

 }