package ru.oleshchuk.module19_kotlin.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.oleshchuk.module19_kotlin.MainActivity
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.data.entity.Film
import ru.oleshchuk.module19_kotlin.databinding.FragmentHomeBinding
import ru.oleshchuk.module19_kotlin.utils.FilmItemAnimation
import ru.oleshchuk.module19_kotlin.utils.FragmentAnimation
import ru.oleshchuk.module19_kotlin.view.adapter.FilmAdapter
import ru.oleshchuk.module19_kotlin.view.adapter.FilmDecoration
import ru.oleshchuk.module19_kotlin.viewmodel.HomeFragmentViewModel
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment(private val position: Int) : Fragment() {

    constructor() : this(0) {
    }

    lateinit var binding: FragmentHomeBinding
    private var filmAdapter: FilmAdapter? = null
    private var rvFilmsView: RecyclerView? = null
    val compositeDisposable = CompositeDisposable()

    /*HomeFragment viewModel*/
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private var filmsDb = listOf<Film>()
        set(value) {
            //Если придет такое же значение, то мы выходим из метода
            if (field == value) return
            field = value

            //Обновляем RV адаптер
            filmAdapter?.addFilms(field)
        }

    /*************************************************************************/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            val disposeFilms = viewModel.getFilms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    filmsDb = it
                    //Убираем крутящееся колечко
                    binding.swipeRefresh.isRefreshing = false
                }
            compositeDisposable.add(disposeFilms)
        }
    }

    /*************************************************************************/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*begin animation*/
        FragmentAnimation.animateFragment(view, requireActivity(), position)
        /**/
        initFilms()
        initSearch()
        initPullToRefresh()

        binding.progressBar.isVisible = true
        val observable = viewModel.getFilms()
        val disposeFilms = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                filmsDb = it
                binding.progressBar.isVisible = false
            }

        val disposeError = viewModel.getError()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.message_no_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        compositeDisposable.addAll(disposeFilms, disposeError)
    }

    /*************************************************************************/
    //search menu
    private fun initSearch() {
        binding.search.setOnClickListener {
            (it as SearchView).isIconified = false
        }

        val searchDisp = Observable.create<String?> {
            emiter ->
            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }
                //Этот метод отрабатывает на каждое изменения текста
                override fun onQueryTextChange(p0: String?): Boolean {
                    if ((p0 == null) || p0.isEmpty()) {
                        //all films
                        emiter.onNext(String())
                        return true
                    }
                    //filter by names
                    emiter.onNext(p0)
                    return true
                }
            })
        }.debounce(1, TimeUnit.SECONDS)
            .flatMap {
                if (it.isEmpty()) {
                    return@flatMap Observable.just(filmsDb)
                } else {
                    return@flatMap viewModel.searchFilmFromApi(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> filmAdapter?.addFilms(response) },
                { t -> Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show() })

        compositeDisposable.add(searchDisp)
    }

    /***********************************************************************/
    init {
        filmAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
            override fun onClick(film: Film?, position: Int) {
                (activity as MainActivity).openFilmDetails(film)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    /*************************************************************************/
    private fun initFilms() {
        rvFilmsView = view?.findViewById<RecyclerView>(R.id.films_view)

        /*set decorations*/
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.layer_divider, null)?.let {
            dividerItemDecoration.setDrawable(it)
            rvFilmsView?.addItemDecoration(dividerItemDecoration)
        }
        val filmDecoration = FilmDecoration(0)
        rvFilmsView?.addItemDecoration(filmDecoration)
        /*set adapter*/
        rvFilmsView?.adapter = filmAdapter
        rvFilmsView?.itemAnimator = FilmItemAnimation(requireContext())
    }
}