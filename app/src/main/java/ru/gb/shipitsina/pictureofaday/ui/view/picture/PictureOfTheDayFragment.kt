package ru.gb.shipitsina.pictureofaday.ui.view.picture
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.gb.shipitsina.pictureofaday.ui.view.ChipsFragment
import kotlinx.android.synthetic.main.main_fragment.*
import ru.gb.shipitsina.pictureofaday.MainActivity
import ru.gb.shipitsina.pictureofaday.R
import ru.gb.shipitsina.pictureofaday.databinding.MainFragmentBinding
import ru.gb.shipitsina.pictureofaday.ui.view.BottomNavigationDrawerFragment
import ru.gb.shipitsina.pictureofaday.ui.viewmodel.PictureOfTheDayData
import ru.gb.shipitsina.pictureofaday.ui.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    val binding: MainFragmentBinding
    get(){
        return _binding!!
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) //Возвращает PictureOfTheDayViewModel для фрагмента
    }
/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it) })
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Подписываемся на получение измененных данных. При изменении данных обрабатываем полученный результат через renderData
        //(отрисовывает картинку при успехе, при ошибке выдает ошибку)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest()

        //Обработка поиска в википедии
        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(binding.includeBottomSheet.bottomSheetContainer)
        setBottomAppBar()
    }

    /**
     * создание менюшки
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //создаем меню menu_bottom_bar
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    /**
     * обработка нажатия кнопок меню
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //обработка нажатия кнопок меню menu_bottom_bar
            R.id.app_bar_fav -> toast("Favourite")
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, ChipsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            //обработка нажатия кнопок меню бургера -> по клику показываем BottomNavigationDrawerFragment
            R.id.home -> BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"")
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Обрабатывает полученные данные
     */
    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                //если успех, то из данных достаем ответ сервера
                // (через PODServerResponseData, в котором хранятся ключи json объекта, по которыс достаем нужное. Например, url)
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url //url с картинкой
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    //картинка подгружается с помощью coil
                    binding.imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_back_fab)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
                //TODO(тут будет анимация при загрузке)
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    /**
     * Установка BottomAppBar (для менюшки: избранное, настройки)
     */
    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                //если на главном меню -> переходим на вложенную вкладку
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                //если не на главном меню -> переходим на главную вкладку
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    /**
     * Установка BottomSheet (для описания картинки )
     */
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
