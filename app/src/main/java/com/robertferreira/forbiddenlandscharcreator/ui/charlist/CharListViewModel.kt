package com.robertferreira.forbiddenlandscharcreator.ui.charlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robertferreira.forbiddenlandscharcreator.database.CharactersDatabaseDAO
import com.robertferreira.forbiddenlandscharcreator.ui.charcreation.CharCreationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharListViewModel(val database: CharactersDatabaseDAO,
                        application: Application) :  AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get()=_text

    init{
        uiScope.launch {
            _text.value = getCharacters()
        }
    }

    suspend fun getCharacters() : String {
        return withContext(Dispatchers.IO) {
            val clist = database.getAllCharacters()
            clist.count().toString()
        }
    }
}

class CharListViewModelFactory(
    private val dataSource: CharactersDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharListViewModel::class.java)) {
            return CharListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}