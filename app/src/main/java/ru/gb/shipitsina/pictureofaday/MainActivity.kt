package ru.gb.shipitsina.pictureofaday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.shipitsina.pictureofaday.ui.view.PictureOfTheDayFragment
import ru.gb.shipitsina.pictureofaday.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}