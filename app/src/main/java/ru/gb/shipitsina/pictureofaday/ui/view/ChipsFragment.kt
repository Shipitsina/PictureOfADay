package ru.gb.shipitsina.pictureofaday.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*
import ru.gb.shipitsina.pictureofaday.R
import ru.gb.shipitsina.pictureofaday.databinding.BottomNavigationLayoutBinding
import ru.gb.shipitsina.pictureofaday.databinding.FragmentChipsBinding
import ru.gb.shipitsina.pictureofaday.databinding.MainFragmentBinding
import ru.gb.shipitsina.pictureofaday.ui.view.picture.PictureOfTheDayFragment

class ChipsFragment : Fragment() {

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
        super.onViewCreated(view, savedInstanceState)
        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                Toast.makeText(context, "Выбран ${it.text}", Toast.LENGTH_SHORT).show()
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
