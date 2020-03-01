package com.robertferreira.forbiddenlandscharcreator.ui.diceroller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random
import kotlin.random.Random.Default

class DiceRollerViewModel(application: Application) : AndroidViewModel(application){

    private val baseDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val bDice : LiveData<MutableList<Int>>
        get() = baseDice

    private val skillDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val sDice : LiveData<MutableList<Int>>
        get() = skillDice

    private val gearDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
    val gDice : LiveData<MutableList<Int>>
        get() = gearDice

    private val otherDice : MutableLiveData<MutableList<Int>> = MutableLiveData<MutableList<Int>>().apply{ value = mutableListOf()}
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
            0 -> baseDice.value?.let{if (it.count() > 0 ) it.removeAt(it.last())}
            1 -> baseDice.value?.let{if (it.count() > 0 ) it.removeAt(it.last())}
            2 -> baseDice.value?.let{if (it.count() > 0 ) it.removeAt(it.last())}
            3 -> baseDice.value?.let{if (it.count() > 0 ) it.removeAt(it.last())}
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

    fun rollDice()
    {
        baseDice.value?.let {
            it.mapInPlace{Random.nextInt(1,6)}
        }
        skillDice.value?.let {
            it.mapInPlace{Random.nextInt(1,6)}
        }
        gearDice.value?.let {
            it.mapInPlace{Random.nextInt(1,6)}
        }
        otherDice.value?.let {
            it.mapInPlace{Random.nextInt(1,6)}
        }
    }
}