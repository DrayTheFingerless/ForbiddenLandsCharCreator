package com.robertferreira.forbiddenlandscharcreator.ui.customviews.gearselect

import android.app.Application
import android.content.ClipData.Item
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robertferreira.forbiddenlandscharcreator.Utils
import com.robertferreira.forbiddenlandscharcreator.models.Gear


class GearSelectViewModel(application: Application) : AndroidViewModel(application) {
    //list of Trade Goods
    private val _gearList = MutableLiveData<List<Gear>>().apply {
        value = Utils.loadGears(this@GearSelectViewModel.getApplication(), "general_gear")
    }
    val gearList: LiveData<List<Gear>> = _gearList


    val newGear = MutableLiveData<Boolean>().apply { value = false }
    private val _gearSelected = MutableLiveData<Gear>()
    val gearSelected: LiveData<Gear>
        get() = _gearSelected


    fun setGear(itemId: Int)
    {
        _gearSelected.value = _gearList.value?.first { it.id == itemId }
        newGear.value = true
    }
}
