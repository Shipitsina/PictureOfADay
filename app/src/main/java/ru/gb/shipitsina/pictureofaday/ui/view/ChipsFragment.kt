package ru.gb.shipitsina.pictureofaday.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*
import ru.gb.shipitsina.pictureofaday.MainActivity
import ru.gb.shipitsina.pictureofaday.R
import ru.gb.shipitsina.pictureofaday.databinding.FragmentChipsBinding
import ru.gb.shipitsina.pictureofaday.ui.model.Parameters


class ChipsFragment : Fragment() {

    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity)
    }

    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get(){
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fun setNewTheme(themeToChange: Int) {
            if (Parameters.getInstance().theme != themeToChange){
                Parameters.getInstance().theme = themeToChange
                parentActivity.setTheme(themeToChange)
                parentActivity.recreate()
            }
        }

        super.onViewCreated(view, savedInstanceState)
        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                if (it.id == R.id.standart_theme){
                    setNewTheme(R.style.Theme_PictureOfADay)
                } else if (it.id == R.id.softness_theme){
                    setNewTheme(R.style.softnessStyle)
                } else if (it.id == R.id.hardness_theme) {
                    setNewTheme(R.style.hardnessStyle)
                }
            }
        }

        chip_close.setOnCloseIconClickListener {
            Toast.makeText(
                context,
                "Close is Clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ChipsFragment
    }
}
