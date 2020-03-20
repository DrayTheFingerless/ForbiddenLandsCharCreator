package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.robertferreira.forbiddenlandscharcreator.*
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabaseDAO
import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData
import kotlinx.coroutines.*

class CharShowViewModel(val database: CharactersDatabaseDAO, application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
    private val _character = PropertyAwareMutableLiveData<FLCharacter>()
    val character
        get() = _character

    val name: LiveData<String> = Transformations.map(_character) { it.Name }
    val kin: LiveData<String> = Transformations.map(_character) {
        Kins.findKin(it.Kin)
    }
    val profession: LiveData<String> = Transformations.map(_character) {
        Professions.findProfession(it.Profession)
    }
    val age: LiveData<String> = Transformations.map(_character) {
          when(it.AgeId){
              0->  "Young ("+it.AgeNumber+")"
              1->  "Adult ("+it.AgeNumber+")"
              2->  "Old ("+it.AgeNumber+")"
              else -> ""
          }
    }
    val charStrength : LiveData<Int> = Transformations.map(_character) { it.Strength }
    val charAgility : LiveData<Int> = Transformations.map(_character) { it.Agility }
    val charWits : LiveData<Int> =  Transformations.map(_character) { it.Wits }
    val charEmpathy : LiveData<Int> = Transformations.map(_character) { it.Empathy }
    val charCurrentStrength : LiveData<Int> = Transformations.map(_character) { it.CurrentStrength }
    val charCurrentAgility : LiveData<Int> = Transformations.map(_character) { it.CurrentAgility }
    val charCurrentWits : LiveData<Int> =  Transformations.map(_character) { it.CurrentWits }
    val charCurrentEmpathy : LiveData<Int> = Transformations.map(_character) { it.CurrentEmpathy }

    val charMight : LiveData<Int> = Transformations.map(_character) {
        it.MySkills.get(Skills.Might) }
    val charEndurance : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Endurance)}
    val charCraft : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Crafting)}
    val charMelee : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Melee)}
    val charStealth : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Stealth) }
    val charSleight : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.SleightOfHand) }
    val charMove : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Move) }
    val charMarksman : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Marksmanship) }
    val charScout : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Scouting) }
    val charLore : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Lore) }
    val charSurvival : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Survival) }
    val charInsight : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Insight) }
    val charManipulation : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Manipulation) }
    val charPerfomance : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Performance) }
    val charHealing : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Healing) }
    val charAnimal : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.AnimalHandling) }

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value = Utils.loadTalents(this@CharShowViewModel.getApplication(), "profession_talents")
    }

    var profTalent: LiveData<String> = Transformations.map(_character) {
        listProfessionTalents.value?.first{ talent ->
            talent.id == it.ProfessionTalent
        }?.name
    }

    var Pride = Transformations.map(_character) { it.Pride }
    var DarkSecret = Transformations.map(_character) { it.DarkSecret }
    var Body = Transformations.map(_character) { it.Body }
    var Face =Transformations.map(_character) { it.Face }
    var Clothing = Transformations.map(_character) { it.Clothing }

    //conditions
    var Hunger = MutableLiveData<Boolean>()
    var Thirst = MutableLiveData<Boolean>()
    var Sleep = MutableLiveData<Boolean>()
    var Cold = MutableLiveData<Boolean>()
    private val HungerObs = Observer<Boolean> {setHunger(it) }
    private val ThirstObs = Observer<Boolean> { setThirst(it) }
    private val SleepObs = Observer<Boolean> { setSleep(it) }
    private val ColdObs = Observer<Boolean> { setCold(it) }

    init {
        Hunger.observeForever(HungerObs)
        Thirst.observeForever(ThirstObs)
        Sleep.observeForever(SleepObs)
        Cold.observeForever(ColdObs)
    }


    override fun onCleared() {
        super.onCleared()
        Hunger.removeObserver(HungerObs)
        Thirst.removeObserver(ThirstObs)
        Sleep.removeObserver(SleepObs)
        Cold.removeObserver(ColdObs)
    }

    fun setCharacter(c: Long){
        uiScope.launch {
            _character.value = getCharacter(c)
            _character.value?.let{
                Hunger.value = it.Hungry
                Thirst.value = it.Thirsty
                Sleep.value = it.Sleepy
                Cold.value = it.Cold
            }
        }
    }

    suspend fun getCharacter(c: Long) : FLCharacter? {
        return withContext(Dispatchers.IO) {
            val c = database.get(c)
            c
        }
    }

    fun addStrength(){
      _character.value?.let{
          if(it.Strength > it.CurrentStrength)
              it.CurrentStrength++
          checkBroken()
          it.notifyChange()
      }
    }
    fun addAgility(){
        _character.value?.let{
            if(it.Agility > it.CurrentAgility)
                it.CurrentAgility++
            checkBroken()
            it.notifyChange()

        }
    }
    fun addWits(){
        _character.value?.let{
            if(it.Wits > it.CurrentWits)
                it.CurrentWits++
            checkBroken()
            it.notifyChange()
        }
    }
    fun addEmpathy(){
        _character.value?.let{
            if(it.Empathy > it.CurrentEmpathy)
                it.CurrentEmpathy++
            checkBroken()
            it.notifyChange()
        }
    }

    fun removeStrength(){
        _character.value?.let{
            if(it.CurrentStrength> 0)
                it.CurrentStrength--
            checkBroken()
            it.notifyChange()
        }
    }
    fun removeAgility(){
        _character.value?.let{
            if(it.CurrentAgility> 0)
                it.CurrentAgility--
            checkBroken()
            it.notifyChange()
        }
    }
    fun removeWits(){
        _character.value?.let{
            if(it.CurrentWits> 0)
                it.CurrentWits--
            checkBroken()
            it.notifyChange()
        }
    }
    fun removeEmpathy(){
        _character.value?.let{
            if(it.CurrentEmpathy> 0)
                it.CurrentEmpathy--
            checkBroken()
            it.notifyChange()
        }
    }

    fun checkBroken()
    {
        _character.value?.let{
            isBroken.value = it.CurrentStrength <= 0 ||
                    it.CurrentAgility <= 0||
                    it.CurrentWits <= 0||
                    it.CurrentEmpathy <= 0
        }
    }

    fun talentClicked(talentId : Int){
        //show a popup with talent info
        _character.value?.TalentList?.first { it.id == talentId }?.let {
            tClicked.value = it
            showTalent.value = true
        }
    }
    fun addTClicked(talentId : Int) {
        _character.value?.let{
            it.ChangeTalent(talentId, true)
        }
    }

    fun removeTClicked(talentId : Int) {
        _character.value?.let{
            it.TalentList?.first{it.id == talentId}?.rankValue?.let{value ->
                if(value > 1)
                    it.ChangeTalent(talentId, false)
            }
        }
    }


    //Gear
    fun addGear(gear: Gear){
        character.value?.let{
            try {
                it.AddGear(gear)
            } catch (e : Exception) {

            }
        }
    }

    fun gearClicked(gear: Gear){
        //show a popup with talent info
        _character.value?.Gear?.first { it == gear }?.let {
            gClicked.value = it
            showGear.value = true
        }
    }

    fun removeGearClicked(gear: Gear) {
        _character.value?.let{
            it.RemoveGear(gear)
        }
    }

    val navigateToGearSelect = MutableLiveData<Boolean>().apply { value = false }
    fun tryAddGear(){
        _character.value?.let{
            navigateToGearSelect.value = true
        }
    }

    fun getGearBonus(skill : Skills) : Int{
        var gearBonus = 0
        character.value?.let{
            for(v in it.Gear){
                if(v.bonusType == skill)
                    gearBonus+=v.bonus
            }
        }
        return gearBonus
    }
    //condition setters
    fun setHunger(hunger: Boolean){
        _character.value?.Hungry = hunger
    }
    fun setThirst(thirst: Boolean){
        _character.value?.Thirsty = thirst
    }
    fun setSleep(sleep: Boolean){
        _character.value?.Sleepy = sleep
    }
    fun setCold(cold: Boolean){
        _character.value?.Cold = cold
    }

    //check states
    var showTalent = MutableLiveData<Boolean>().apply { value = false }
    var showGear = MutableLiveData<Boolean>().apply { value = false }

    var isBroken = MutableLiveData<Boolean>().apply { value = false }

    val gClicked = MutableLiveData<Gear>()
    val tClicked = MutableLiveData<Talent>()

    //update char
    fun saveCharacter() {
        character.value?.let {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    database.update(it)
                }
            }
        }
    }
}

class CharShowViewModelFactory(
    private val dataSource: CharactersDatabaseDAO,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharShowViewModel::class.java)) {
            return CharShowViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}