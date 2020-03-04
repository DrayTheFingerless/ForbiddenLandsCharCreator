package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.Utils.loadTalents
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabaseDAO
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CharCreationViewModel(val database: CharactersDatabaseDAO,
                                application: Application) :  AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _creationDone = MutableLiveData<Boolean>()
    val creationDone : LiveData<Boolean>
        get() = _creationDone

    //Character to be saved
    private val character = PropertyAwareMutableLiveData<FLCharacter>().apply{ value = FLCharacter() }
    val char: LiveData<FLCharacter>
        get() = character

    //observer attribute fields
    val attrPoints : LiveData<Int>
        get () = Transformations.map(character) {val i = when(it.AgeId){
            0-> 15 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            1-> 14 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            2-> 13 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            else-> 13 - (it.Strength+it.Agility+it.Wits+it.Empathy)
        }
            i
    }
    val charStrength : LiveData<Int>
        get() = Transformations.map(character) { it.Strength }
    val charAgility : LiveData<Int>
        get() = Transformations.map(character) { it.Agility }
    val charWits : LiveData<Int>
        get() =  Transformations.map(character) { it.Wits }
    val charEmpathy : LiveData<Int>
        get() = Transformations.map(character) { it.Empathy }

    //observer skill fields
    val skillPoints : LiveData<Int>
        get () = Transformations.map(character) {val i = when(it.AgeId){
            0-> 8 - it.MySkills?.values.sum()
            1-> 10 - it.MySkills?.values.sum()
            2-> 12 - it.MySkills?.values.sum()
            else-> 8 - it.MySkills?.values.sum()
        }
            i
        }
    val charMight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Might) ?: 0}
    val charEndurance : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Endurance) ?: 0}
    val charCraft : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Crafting) ?: 0}
    val charMelee : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Melee) ?: 0}
    val charStealth : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Stealth) ?: 0}
    val charSleight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.SleightOfHand) ?: 0}
    val charMove : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Move) ?: 0}
    val charMarksman : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Marksmanship) ?: 0}
    val charScout : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Scouting) ?: 0}
    val charLore : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Lore) ?: 0}
    val charSurvival : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Survival) ?: 0}
    val charInsight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Insight) ?: 0}
    val charManipulation : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Manipulation) ?: 0}
    val charPerfomance : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Performance) ?: 0}
    val charHealing : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Healing) ?: 0}
    val charAnimal : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.AnimalHandling) ?: 0}


    private val kTName = MutableLiveData<String>()
    val kinTalentName : LiveData<String>
        get() = kTName


    //list of Kin Talents
    private var listKinTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"kin_talents") }
    val kTalents : LiveData<List<Talent>>
        get() = listKinTalents

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"profession_talents") }

    //list of Profession Talents Filtered by Profession selected
    private var filterListProfessionTalents = MutableLiveData<List<Talent>>().apply { value =  listOf() }
    val pTalents : LiveData<List<Talent>>
        get() = filterListProfessionTalents


    //list of General Talents
    private val listGeneralTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"general_talents") }
    val gTalents : LiveData<List<Talent>> = listGeneralTalents

    init{

    }

    //filters from all profession talents into a filtered list for Prof Talents Spinner
    fun getFilteredProfessionTalents(profId : Int)  {
        Log.i("filtering talents", profId.toString())
        filterListProfessionTalents.value = listProfessionTalents.value?.filter{it.type == profId}
        print(filterListProfessionTalents.value?.count())
    }


    var Name = MutableLiveData<String>()

    private val NameObserver = Observer<String> {
        onNameChanged(it)
    }
    var Pride = MutableLiveData<String>()

    private val PrideObserver = Observer<String> {
        setPride(it)
    }

    var DarkSecret = MutableLiveData<String>()

    private val DarkSecretObserver = Observer<String> {
        setDarkSecret(it)
    }
    init {
        Name.observeForever(NameObserver)
        Pride.observeForever(PrideObserver)
        DarkSecret.observeForever(DarkSecretObserver)
    }

    override fun onCleared() {
        super.onCleared()

        Name.removeObserver(NameObserver)
        Pride.removeObserver(PrideObserver)
        DarkSecret.removeObserver(DarkSecretObserver)
        viewModelJob.cancel()

    }

    fun onNameChanged(newName: String) {
        // Some code
        character.value?.Name = newName
    }


    fun SelectKin(position : Int){
        character.value?.UpdateKin(position)
        kTName.value = kTalents.value?.first{ it.id == character.value?.Kin }?.name ?: "None"
    }

    fun SelectProfession(position : Int){
        character.value?.let{ch->
            getFilteredProfessionTalents(position)
            ch.UpdateProfession(position)
        }
    }

    fun SelectProfessionTalent(position : Int){
        character.value?.let{ch->
            ch.ProfessionTalent = position
        }
    }
    fun SelectAge(position : Int, value : Int){
        character.value?.let{ch->
            ch.UpdateAge(position, value)
        }
    }

    fun IncrementAttribute(idAttribute : Attributes)
    {
        character.value?.let {
            if(it.AttrPointsLeft() > 0)
                it.IncrementAttribute(idAttribute)
        }
    }

    fun DecrementAttribute(idAttribute : Attributes)
    {   character.value?.let{
           it.DecrementAttribute(idAttribute)
        }
    }

    fun ChangeSkill(idSkill : Skills, addOrNot : Boolean)
    {
        character.value?.let {
            it.ChangeSkill(idSkill, addOrNot)
        }
    }



    fun setPride(pride : String){
        character.value?.Pride = pride
    }
    fun setDarkSecret(secret : String){
        character.value?.DarkSecret = secret
    }
    fun addRelationship(name: String, description: String){
        character.value?.let{
            it.AddRelationship(name,description)
        }
    }
    fun removeRelationship(name: String){
        character.value?.let{
            it.RemoveRelationShip(name)
        }
    }

    fun addGear(gearId: Int, name: String){
        character.value?.let{
            it.AddGear(gearId,name)
        }
    }
    fun removeRelationship(gearId: Int){
        character.value?.let{
            it.RemoveGear(gearId)
        }
    }

    fun saveCharacter(){
        character.value?.let {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    database.insert(it)
                }
                _creationDone?.value = true
            }
        }
    }
}


class CharCreationViewModelFactory(
    private val dataSource: CharactersDatabaseDAO,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharCreationViewModel::class.java)) {
            return CharCreationViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}