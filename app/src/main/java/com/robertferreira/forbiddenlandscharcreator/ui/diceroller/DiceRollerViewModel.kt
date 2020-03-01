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

    private var otherDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val oDice : LiveData<MutableList<Int>>
        get() = otherDice


    fun addDie(type : Int)
    {
        when(type){
            0 -> baseDice.value?.add(1)
            1 -> skillDice.value?.add(1)
            2 -> gearDice.value?.add(1)
            3 -> otherDice.value?.add(1)
            else -> return
        }
    }

    fun removeDie(type : Int)
    {
        when(type){
            0 -> baseDice.value?.let{ if (it.count() > 0 ) it.removeAt(it.lastIndex) }
            1 -> skillDice.value?.let{if (it.count() > 0 ) it.removeAt(it.lastIndex)}
            2 -> gearDice.value?.let{if (it.count() > 0 ) it.removeAt(it.lastIndex)}
            3 -> otherDice.value?.let{if (it.count() > 0 ) it.removeAt(it.lastIndex)}
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
        otherDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                temp.set(x , Random.nextInt(1,7))
            otherDice.value = temp
        }
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
        otherDice.value?.let {
            var temp = it
            for(x in 0 until it.count())
                if(temp[x] != 6 && temp[x] != 1)
                    temp.set(x , Random.nextInt(1,7))
            otherDice.value = temp
        }
    }
}