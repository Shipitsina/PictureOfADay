package ru.gb.shipitsina.pictureofaday.ui.viewmodel

import ru.gb.shipitsina.pictureofaday.ui.model.PODServerResponseData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}

