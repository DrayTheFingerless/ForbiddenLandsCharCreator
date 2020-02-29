package com.robertferreira.forbiddenlandscharcreator

import android.util.Log
import org.junit.Test

import org.junit.Before
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTests {

    private lateinit var baseDice : MutableList<Int>

    @Before
    fun createList(){
        baseDice = mutableListOf(1,1,1,1)
    }

    @Test
    fun rollDice()
    {

        baseDice?.let {
            it.mapInPlace{ Random.nextInt(1,6)}
            it.forEach{ a -> println(a.toString())}
        }
    }

    fun <T> MutableList<T>.mapInPlace(mutator: (T)->T) {
        val iterate = this.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val newValue = mutator(oldValue)
            if (newValue !== oldValue) {
                iterate.set(newValue)
            }
        }
    }

}