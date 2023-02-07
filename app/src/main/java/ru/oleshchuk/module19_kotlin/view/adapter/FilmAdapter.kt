package ru.oleshchuk.module19_kotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.data.ApiConsts
import ru.oleshchuk.module19_kotlin.databinding.FilmItemBinding
import ru.oleshchuk.module19_kotlin.domain.Film

class FilmAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    private val films = mutableListOf<Film>()

    class FilmHolder(val item : View) : RecyclerView.ViewHolder(item) {
        private val binding = FilmItemBinding.bind(item)
        var holdFilm: Film? = null

        fun bind(film: Film)= with(binding){
            filmName.text = film.name
            filmDesc.text = film.desc
            //Указываем контейнер, в котором будет "жить" наша картинка
            Glide.with(item)
                //Загружаем сам ресурс
                .load(ApiConsts.TMDB_IMAGES_URL + "w342" + film.posterId)
                //Центруем изображение
                .centerCrop()
                //Указываем ImageView, куда будем загружать изображение
                .into(filmPoster)
            /*save film*/
            holdFilm = film
            vwRating.setRating((film.rate * 10).toInt())
        }
    }

    interface OnItemClickListener{
        fun onClick(film: Film?, position : Int)
    }

    fun addFilms(newFilms: List<Film>){
        val diffResult = DiffUtil.calculateDiff(FilmDiff(films, newFilms))
        films.clear()
        films.addAll(newFilms)
        //notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(films[position])
        holder.itemView.findViewById<CardView>(R.id.film_card).setOnClickListener {
            /*on click by film card*/
            onItemClickListener.onClick(holder.holdFilm, position)
        }
        val binding = FilmItemBinding.bind(holder.itemView)
        binding.vwRating.startAnimation()
    }

    override fun getItemCount(): Int {
        return films.size
    }

}