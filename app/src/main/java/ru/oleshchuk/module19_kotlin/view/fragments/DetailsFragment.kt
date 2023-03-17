package ru.oleshchuk.module19_kotlin.view.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.data.ApiConsts
import ru.oleshchuk.module19_kotlin.data.Args
import ru.oleshchuk.module19_kotlin.databinding.FragmentDetailsBinding
import ru.oleshchuk.module19_kotlin.domain.Film
import ru.oleshchuk.module19_kotlin.viewmodel.DetailsFragmentViewModel

class DetailsFragment : Fragment() {

    private lateinit var binding : FragmentDetailsBinding
    private val viewModel : DetailsFragmentViewModel by viewModels()
//    lazy {
//        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
//    }

    //Узнаем, было ли получено разрешение ранее
    private fun checkPermission() : Boolean{
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    //Запрашиваем разрешение
    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun String.handleSingleQuote() : String{
        return this.replace("'", "")
    }

    private fun saveToGalary(bitmap: Bitmap, film: Film){
        //Проверяем версию системы
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val contentValues = ContentValues().apply {
                //Составляем информацию для файла (имя, тип, дата создания, куда сохранять и т.д.)
                put(MediaStore.Images.Media.TITLE, film.posterId.handleSingleQuote())
                put(MediaStore.Images.Media.DISPLAY_NAME, film.posterId.handleSingleQuote())
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis()/1000)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilmsSearchApp")
            }
            //Получаем ссылку на объект Content resolver, который помогает передавать информацию из приложения вовне
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            //Открываем канал для записи на диск
            val outputStream = contentResolver.openOutputStream(uri!!)
            //Передаем нашу картинку, может сделать компрессию
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            //Закрываем поток
            outputStream?.close()
        }
        else{
            //То же, но для более старых версий ОС
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.posterId.handleSingleQuote(),
                film.desc!!.handleSingleQuote()
            )
        }

    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private fun performAsyncLoadOfPoster(film: Film) {
        //Проверяем есть ли разрешение
        if(!checkPermission()){
            //Если нет, то запрашиваем и выходим из метода
            requestPermission()
            return
        }
      //Создаем родительский скоуп с диспатчером Main потока, так как будем взаимодействовать с UI
        MainScope().launch {
            //Включаем Прогресс-бар
            binding.progressBar.isVisible = true
            //Создаем через async, так как нам нужен результат от работы, то есть Bitmap
            val defferedBitmap = scope.async {
                viewModel.loadWallpaper(ApiConsts.TMDB_IMAGES_URL+"original"+film.posterId)
            }
            //Сохраняем в галерею, как только файл загрузится
            val loadBitmap = defferedBitmap.await()
            if(loadBitmap ==null){
                /*ошибка*/
                Toast.makeText(requireContext(), R.string.load_error, Toast.LENGTH_SHORT).show()
            }
            else {
                saveToGalary(loadBitmap, film)
                //Выводим снекбар с кнопкой перейти в галерею
                Snackbar.make(
                    binding.root,
                    R.string.download_to_galary,
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.open){
                    val intent = Intent()
                    intent.action = Intent.ACTION_PICK
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }.show()
            }
            //Отключаем Прогресс-бар
            binding.progressBar.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    init {
        enterTransition = Fade(Fade.MODE_IN).setDuration(500)
        returnTransition= Fade(Fade.MODE_OUT).setDuration(200)
    }

    /*set type image*/
    private fun setFabFavourite(isFav : Boolean) {
        if (isFav){
            binding.fabDetailsFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            binding.fabDetailsFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    /*************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = arguments?.getParcelable<Film>(Args.FILM_ARG)
        film?.apply {


            Glide.with(requireContext())
                .load(ApiConsts.TMDB_IMAGES_URL + "w342" + posterId)
                .centerCrop()
                .into(binding.centralPoster)
            //binding.centralPoster.setImageResource( posterId)
            binding.detailsDesc.text = desc
            binding.detailsToolbar.title = name
            /*set type image*/
            setFabFavourite (this.isFav)
        }
        /**/
        binding.fabDetailsFavourite.setOnClickListener {
            film?.apply {
                isFav = !isFav
                setFabFavourite(isFav)
            }
        }
        binding.fabDetailsDownload.setOnClickListener {
            if(film!=null) {
                performAsyncLoadOfPoster(film)
            }
        }
        binding.fabDetailsShare.setOnClickListener {
            if(film!=null){
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT,
                    "film(${film.name}, desc(${film.desc})")
                //текстовые данные
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Choose"))
            }
        }
    }
}