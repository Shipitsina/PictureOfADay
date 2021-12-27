package ru.gb.shipitsina.pictureofaday.ui.model

import ru.gb.shipitsina.pictureofaday.R

class Parameters {

    var resetFragment: Boolean = false
    var theme: Int = R.style.softnessStyle//Theme_PictureOfADay_NoActionBar

    companion object {
        @Volatile
        private var INSTANCE: Parameters? = null
        fun getInstance(): Parameters {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Parameters()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}