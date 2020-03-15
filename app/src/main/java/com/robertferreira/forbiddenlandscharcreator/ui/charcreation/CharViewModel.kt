package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robertferreira.forbiddenlandscharcreator.*
import com.robertferreira.forbiddenlandscharcreator.Utils.loadTalents
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabaseDAO
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CharViewModel(val database: CharactersDatabaseDAO,
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
    private var listKinTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents(this@CharViewModel.getApplication(),"kin_talents") }
    val kTalents : LiveData<List<Talent>>
        get() = listKinTalents

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents(this@CharViewModel.getApplication(),"profession_talents") }

    //list of Profession Talents Filtered by Profession selected
    private var filterListProfessionTalents = MutableLiveData<List<Talent>>()
    val pTalents : LiveData<List<Talent>>
        get() = filterListProfessionTalents

    //list of General Talents
    private val listGeneralTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents(this@CharViewModel.getApplication(),"general_talents") }
    val gTalents : LiveData<List<Talent>> = listGeneralTalents

    init{

    }

    //filters from all profession talents into a filtered list for Prof Talents Spinner
    fun getFilteredProfessionTalents(profId : Int)  {
        filterListProfessionTalents.value = listProfessionTalents.value?.filter{it.type == profId}
        print(filterListProfessionTalents.value?.count())
    }


    var Name = MutableLiveData<String>()

    private val NameObserver = Observer<String> {
        onNameChanged(it)
    }
    var AgeNumber = MutableLiveData<String>()

    private val AgeNumberObserver = Observer<String> {
        onAgeChanged(it)
    }
    var Pride = MutableLiveData<String>()

    private val PrideObserver = Observer<String> {
        setPride(it)
    }

    var DarkSecret = MutableLiveData<String>()

    private val DarkSecretObserver = Observer<String> {
        setDarkSecret(it)
    }

    var Body = MutableLiveData<String>()

    private val BodyObserver = Observer<String> {
        setBody(it)
    }

    var Face = MutableLiveData<String>()

    private val FaceObserver = Observer<String> {
        setFace(it)
    }

    var Clothing = MutableLiveData<String>()

    private val ClothingObserver = Observer<String> {
        setClothing(it)
    }

    init {
        Name.observeForever(NameObserver)
        AgeNumber.observeForever(AgeNumberObserver)

        Pride.observeForever(PrideObserver)
        DarkSecret.observeForever(DarkSecretObserver)
        Body.observeForever(BodyObserver)
        Face.observeForever(FaceObserver)
        Clothing.observeForever(ClothingObserver)

    }


    override fun onCleared() {
        super.onCleared()

        Name.removeObserver(NameObserver)
        AgeNumber.removeObserver(AgeNumberObserver)
        Pride.removeObserver(PrideObserver)
        DarkSecret.removeObserver(DarkSecretObserver)
        Body.removeObserver(BodyObserver)
        Face.removeObserver(FaceObserver)
        Clothing.removeObserver(ClothingObserver)
        viewModelJob.cancel()

    }

    fun onNameChanged(newName: String) {
        // Some code
        character.value?.Name = newName
    }

    fun onAgeChanged(newAge: String) {
        try {
            val i = newAge.toInt()
            character.value?.AgeNumber = i
        } catch (nfe: NumberFormatException) {
            character.value?.AgeNumber = 0
        }
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

    fun SelectProfessionTalent(talent : Talent?){
        character.value?.let{ch->
            ch.ProfessionTalent = talent?.id ?: 0
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

    fun setName(name : String){
        character.value?.Name = name
    }

    fun setPride(pride : String){
        character.value?.Pride = pride
    }
    fun setDarkSecret(secret : String){
        character.value?.DarkSecret = secret
    }
    fun setBody(b : String){
        character.value?.Body = b
    }
    fun setFace(f : String){
        character.value?.Face = f
    }
    fun setClothing(c : String){
        character.value?.Clothing = c
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

    fun addTalent(item: Talent){
        character.value?.let{
            if(!it.TalentList.contains(item))
                it.AddTalent(item)
            else Toast.makeText(getApplication(),"Talent already added", Toast.LENGTH_SHORT).show()
        }
    }

    //observe talents
    //observer skill fields
    val talentPoints : LiveData<Int>
        get () = Transformations.map(character) {
            val sum = it.TalentList.sumBy { it.rankValue }
            val i = when(it.AgeId){
                0-> 1 - sum
                1-> 2 - sum
                2-> 3 - sum
                else-> 3 - sum
            }
            i
    }
    fun talentClicked(talentId : Int){
        //show a popup with talent info
        character.value?.TalentList?.first { it.id == talentId }?.let {
            tClicked.value = it
            showTalent.value = true
        }
    }
    val navigateToAddTalent = MutableLiveData<Boolean>().apply { value = false }
    fun tryAddTalent(){
        character.value?.let{
            if (it.TalentPointsleft() > 0)
                navigateToAddTalent.value = true
        }
    }

    var showTalent = MutableLiveData<Boolean>().apply { value = false }
    val tClicked = MutableLiveData<Talent>()

    fun removeTClicked(talentId : Int) { character.value?.ChangeTalent(talentId, false) }
    fun addTClicked(talentId : Int) {
        character.value?.let {
            if(it.TalentPointsleft() > 0)
                character.value?.ChangeTalent(talentId, true)
        }
    }


    //Gear

    val maxWeight : LiveData<Int>
        get () = Transformations.map(character) {
            val sum = it.Strength * 2
            sum
        }

    fun addGear(gearId: Int){
        character.value?.let{
            try {
               // val w = weight.toFloat()
                //it.AddGear(name, w)
            } catch (e : Exception) {

            }
        }
    }

    fun gearClicked(gearId: Int){
        character.value?.let{
            try {
                // val w = weight.toFloat()
                //it.AddGear(name, w)
            } catch (e : Exception) {

            }
        }
    }

    fun removeGearClicked(gearId: Int) {
        character.value?.let{
            it.RemoveGear(gearId)
        }
    }

    val navigateToGearSelect = MutableLiveData<Boolean>().apply { value = false }
    fun tryAddGear(){
        character.value?.let{
                navigateToGearSelect.value = true
        }
    }

    fun saveCharacter() {
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
        if (modelClass.isAssignableFrom(CharViewModel::class.java)) {
            return CharViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}