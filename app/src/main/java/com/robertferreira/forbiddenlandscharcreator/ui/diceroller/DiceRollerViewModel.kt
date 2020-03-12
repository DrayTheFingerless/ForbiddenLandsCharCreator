package com.robertferreira.forbiddenlandscharcreator.ui.diceroller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random
import kotlin.random.Random.Default

class DiceRollerViewModel(application: Application) : AndroidViewModel(application){

    private var baseDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val bDice : LiveData<MutableList<Int>>
        get() = baseDice

    private var skillDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val sDice : LiveData<MutableList<Int>>
        get() = skillDice

    private var gearDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val gDice : LiveData<MutableList<Int>>
        get() = gearDice

    private var _d8Dice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val d8Dice : LiveData<MutableList<Int>>
        get() = _d8Dice
    private var _d10Dice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val d10Dice : LiveData<MutableList<Int>>
        get() = _d10Dice
    private var _d12Dice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val d12Dice : LiveData<MutableList<Int>>
        get() = _d12Dice

    private val _diceRolled = MutableLiveData<Boolean>()
    val diceRolled : LiveData<Boolean>
        get() = _diceRolled

    private val _dicePushRolled = MutableLiveData<Boolean>()
    val dicePushRolled : LiveData<Boolean>
        get() = _dicePushRolled

    init {
        _diceRolled.value = false
        _dicePushRolled.value = false
    }

    fun addDie(type : Int) {
        when(type){
            0 -> baseDice.value?.add(1)
            1 -> skillDice.value?.add(1)
            2 -> gearDice.value?.add(1)
            3 -> d8Dice.value?.add(1)
            4 -> d10Dice.value?.add(1)
            5 -> d12Dice.value?.add(1)
            else -> return
        }
    }

    fun removeDie(type : Int) {
        when(type) {
            0 -> baseDice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            1 -> skillDice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            2 -> gearDice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            3 -> d8Dice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            4 -> d10Dice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            5 -> d12Dice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            else -> return
        }
    }

    inline fun <T> MutableList<T>.mapInPlace(mutator: (T)->T) {
        val iterate = this.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val newValue = mutator(oldValue)
            if (newValue !== oldValue) {
                iterate.set(newValue)
            }
        }
    }

    fun rollDice() {
        baseDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,7))
            baseDice.value = temp
        }
        skillDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,7))
            skillDice.value = temp
        }
        gearDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,7))
            gearDice.value = temp
        }
        _d8Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,9))
            _d8Dice.value = temp
        }
        _d10Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,11))
            _d10Dice.value = temp
        }
        _d12Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,13))
            _d12Dice.value = temp
        }
        _diceRolled.value = true
    }

    fun pushRollDice() {
        baseDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] != 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,7))
            baseDice.value = temp
        }
        skillDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] != 6)
                    temp.set(x , Random.nextInt(1,7))
            skillDice.value = temp
        }
        gearDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] != 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,7))
            gearDice.value = temp
        }
        _d8Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] < 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,9))
            _d8Dice.value = temp
        }
        _d10Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] < 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,11))
            _d10Dice.value = temp
        }
        _d12Dice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] < 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,13))
            _d12Dice.value = temp
        }
        _dicePushRolled.value = true
    }

    fun onDiceRolled(){
        _diceRolled.value = false
        _dicePushRolled.value = false
    }


}