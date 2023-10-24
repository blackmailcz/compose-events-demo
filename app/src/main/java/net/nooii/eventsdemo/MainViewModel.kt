package net.nooii.eventsdemo

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val oneTimeEvent: OneTimeEvent
        get() = _oneTimeEvent
    private val _oneTimeEvent = MutableOneTimeEvent()

    val oneTimeDataEvent: OneTimeDataEvent<String>
        get() = _oneTimeDataEvent
    private val _oneTimeDataEvent = MutableOneTimeDataEvent<String>()

    fun onButton1Clicked() {
        _oneTimeEvent.emit()
    }

    fun onButton2Clicked() {
        _oneTimeDataEvent.emit("Variable")
    }
}