package ru.gb.shipitsina.pictureofaday.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.shipitsina.pictureofaday.BuildConfig
import ru.gb.shipitsina.pictureofaday.ui.model.PODRetrofitImpl
import ru.gb.shipitsina.pictureofaday.ui.model.PODServerResponseData

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(), // PictureOfTheDayData - состояние загрузки картинки: успех/загружается/ошибка
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    /**
     * Метод возвращает Данные в PictureOfTheDateFragment, когда с ними что-то происходит (через Observer)
     */
    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    /**
     * Метод возвращает ответ сервера NASA
     */
    fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            //enqueue - добавление в очередь. Ответ передаем в callback
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    val callback = object :Callback<PODServerResponseData>{
        //Если сервер ответил
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                //Устанавливаем состояние liveDataForViewToObserve в Success
                liveDataForViewToObserve.value =
                    PictureOfTheDayData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Error(Throwable("Unidentified error"))
                } else {
                    liveDataForViewToObserve.value =
                        PictureOfTheDayData.Error(Throwable(message))
                }
            }
        }
        //Если сервер не ответил :(
        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
        }
    }
}

