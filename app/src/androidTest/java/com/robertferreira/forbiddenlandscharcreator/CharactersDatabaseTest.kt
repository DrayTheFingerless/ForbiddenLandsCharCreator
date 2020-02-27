package com.robertferreira.forbiddenlandscharcreator

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabase
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabaseDAO
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CharactersDatabaseTest{
    private lateinit var characterDao : CharactersDatabaseDAO
    private lateinit var db : CharactersDatabase


    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(
            context,
            CharactersDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        characterDao = db.charactersDatabaseDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCharacter(){
        val character = FLCharacter()
        characterDao.insert(character)
        val getChar = characterDao.getLastCharacter()

        assertEquals(character.Name,getChar?.Name)
    }

}