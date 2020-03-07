package com.robertferreira.forbiddenlandscharcreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.SkillConverter
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.utils.Converters

@Database(entities =[FLCharacter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class,SkillConverter::class)
abstract class CharactersDatabase : RoomDatabase(){

    abstract fun charactersDatabaseDAO(): CharactersDatabaseDAO

    companion object{

        @Volatile
        private var INSTANCE : CharactersDatabase? = null

        fun getInstance(context : Context) : CharactersDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance== null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharactersDatabase::class.java,
                        "characters_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }


                return instance
            }
        }
    }
}