package com.robertferreira.forbiddenlandscharcreator

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.robertferreira.forbiddenlandscharcreator.Attributes.Agility
import com.robertferreira.forbiddenlandscharcreator.Attributes.Empathy
import com.robertferreira.forbiddenlandscharcreator.Attributes.Wits
import org.w3c.dom.Attr

class FLCharacter : BaseObservable() {


     var Name: String = ""
     var Kin: Kin = Kins.kins[0]
     var Profession: Profession = Professions.professions[0]

     //0 - Young, 1 - Adult, 2 - Old
     var AgeId: Int = 0
     var AgeNumber: Int = 0

    @Bindable var Strength: Int = 2
    @Bindable var Agility: Int = 2
    @Bindable var Wits: Int = 2
    @Bindable var Empathy: Int = 2

     var Pride: String = ""
     var DarkSecret: String = ""
     var Reputation: Int = 0

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
                 MySkills.put(it.key, 1)
            }
         }

         //insert Gear logics
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

         var currentTotalAttr = Strength + Agility + Wits + Empathy
         var currentTotalSkills = MySkills.values.sum()

         if(currentTotalAttr > maxAttr) {
             Strength = 2
             Agility = 2
             Wits = 2
             Empathy = 2
         }


         if(currentTotalSkills > maxSkill){
            MySkills.forEach{MySkills.set(it.key, 0)}
         }

         notifyChange()
     }

     private fun UpdateKeyAttributes(previousAttribute : Attributes, attribute : Attributes){
         if(previousAttribute != attribute) {
             //check which attribute key was previous, then reduce value of previous key attribute, check if new max is higher than current value,
             //if current value higher than max, decrement current and add that point back to Attribute pool
             Strength = 2
             Agility = 2
             Wits = 2
             Empathy = 2
         }
         notifyChange()

     }

    fun IncrementAttribute(attribute: Attributes ){
         var max = 4
         if(Profession.KeyAttribute == attribute)
             max++
         if(Kin.KeyAttribute == attribute)
             max++

         when (attribute) {
             Attributes.Strength -> if(Strength < max) { Strength++
                 notifyPropertyChanged(this.Strength)}
             Attributes.Agility -> if(Agility < max) {
                 Agility++
                 notifyPropertyChanged(this.Agility)
                }
             Attributes.Wits -> if(Wits < max) { Wits++
                notifyPropertyChanged(this.Wits)}
             Attributes.Empathy -> if(Empathy < max){ Empathy++
                 notifyPropertyChanged(this.Empathy)
             }
         }
         notifyChange()
     }

    fun DecrementAttribute(attribute: Attributes ){
        when (attribute) {
            Attributes.Strength -> if(Strength > 2) {Strength--
                notifyPropertyChanged(this.Strength)
            }
            Attributes.Agility -> if(Agility > 2){ Agility--
                notifyPropertyChanged(this.Agility)
            }
            Attributes.Wits -> if(Wits > 2){ Wits--
                notifyPropertyChanged(this.Wits)
            }
            Attributes.Empathy -> if(Empathy > 2) {Empathy--
                notifyPropertyChanged(this.Empathy)
            }
        }
        notifyChange()

    }

    fun ChangeSkill(skill: Skills, addOrSubtract: Boolean)
    {
        var max = 1
        if(Profession.Skills.contains(skill))
            max = 3
        MySkills.get(skill)?.let{ va ->
            if(addOrSubtract && va < max && SkillPointsLeft() > 0)
                MySkills.set(skill,va+1)
            else if(!addOrSubtract && va > 0)
                MySkills.set(skill,va-1)
        }

        notifyChange()
    }

    fun AttrPointsLeft() : Int{
        val currentUsed = Strength+Agility+Wits+Empathy
        when(AgeId){
            0 -> return 15 - currentUsed
            1 -> return 14 - currentUsed
            2 -> return 13 - currentUsed
            else -> return 13 - currentUsed
        }
    }

    fun SkillPointsLeft() : Int {
        val currentUsed = MySkills.values.sum()
        when(AgeId){
            0 -> return 8 - currentUsed
            1 -> return 10 - currentUsed
            2 -> return 12 - currentUsed
            else -> return 8 - currentUsed
        }
    }
 }