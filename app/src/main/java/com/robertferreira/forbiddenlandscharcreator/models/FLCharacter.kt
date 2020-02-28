package com.robertferreira.forbiddenlandscharcreator

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.robertferreira.forbiddenlandscharcreator.Attributes.Agility
import com.robertferreira.forbiddenlandscharcreator.Attributes.Empathy
import com.robertferreira.forbiddenlandscharcreator.Attributes.Wits
import com.robertferreira.forbiddenlandscharcreator.utils.Converters
import kotlinx.android.parcel.Parcelize
import org.w3c.dom.Attr
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Entity(tableName = "characters_table")
class FLCharacter(

    @PrimaryKey(autoGenerate = true)
    var charId: Long = 0L,

    @ColumnInfo(name = "created")
    var created: Instant = Instant.now(),

    @ColumnInfo(name = "modified")
    var modified: Instant = Instant.now(),

    @ColumnInfo(name = "name")
    var Name: String = "",

    @ColumnInfo(name = "kin")
    var Kin: Int = 0,

    @ColumnInfo(name = "profession")
    var Profession: Int = 0,

    //0 - Young, 1 - Adult, 2 - Old
    @ColumnInfo(name = "age")
    var AgeId: Int = 0,
    @ColumnInfo(name = "agenumber")
    var AgeNumber: Int = 0,

    @ColumnInfo(name = "strength")
    @Bindable var Strength: Int = 2,
    @ColumnInfo(name = "agility")
    @Bindable var Agility: Int = 2,
    @ColumnInfo(name = "wits")
    @Bindable var Wits: Int = 2,
    @ColumnInfo(name = "empathy")
    @Bindable var Empathy: Int = 2,

    @ColumnInfo(name = "pride")
     var Pride: String = "",
    @ColumnInfo(name = "secret")
     var DarkSecret: String = "",
    @ColumnInfo(name = "reputation")
     var Reputation: Int = 0,



    @ColumnInfo(name = "face")
     var Face: String = "",
    @ColumnInfo(name = "body")
     var Body: String = "",
    @ColumnInfo(name = "clothing")
     var Clothing: String = "",
    @ColumnInfo(name = "willpower")
     var CurrentWillPoints : Int = 0,


    @ColumnInfo(name = "carrycapacity")
     var CarryCapacity: Int =  0,

    var MySkills : MutableMap<Skills, Int> = mutableMapOf(),

    var TalentList: ArrayList<Talent> = arrayListOf(),

    var Relationships: HashMap<String, String> = hashMapOf(),

    var Gear: HashMap<Int, String> = hashMapOf(),

     //0 = none, 1 = d6, 2 = d8, 3 = d10, 4 = d12
    @ColumnInfo(name = "food")
     var FoodDie: Int = 1,
    @ColumnInfo(name = "water")
     var WaterDie: Int = 1,
    @ColumnInfo(name = "arrows")
     var ArrowsDie: Int = 0,
    @ColumnInfo(name = "torches")
     var TorchesDie: Int = 0,


     //in Silver
    @ColumnInfo(name = "money")
     var Money: Int = 0

) : BaseObservable() {

    init {
         enumValues<Skills>().forEach { MySkills.set(it, 0)  }
     }

    fun UpdateKin(newkin : Int)
     {
         val previousKin = Kins.kins.first{this.Kin == it.KinId}
         this.Kin = newkin
         UpdateKeyAttributes(previousKin.KeyAttribute, Kins.kins.first{newkin == it.KinId}.KeyAttribute)

     }

     fun UpdateProfession(newprofession : Int)
     {
         val previousProfession = Professions.professions.first{this.Profession == it.ProfessionId}
         this.Profession = newprofession
         UpdateKeyAttributes(previousProfession.KeyAttribute, Professions.professions.first{newprofession == it.ProfessionId}.KeyAttribute)

         //if any non key skill is above 1, update skill point pull and reset sklls to 1
         MySkills.forEach{
             if(!Professions.professions.first{newprofession == it.ProfessionId}.Skills.contains(it.key) && it.value > 1) {
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
         if(Professions.professions.first{Profession == it.ProfessionId}.KeyAttribute == attribute)
             max++
         if(Kins.kins.first{Kin == it.KinId}.KeyAttribute == attribute)
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
        if(Professions.professions.first{Profession == it.ProfessionId}.Skills.contains(skill))
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