package ru.oleshchuk.module19_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.databinding.FilmItemBinding
import ru.oleshchuk.module19_kotlin.model.Film

class FilmAdapter( private val onItemClickListener: FilmAdapter.OnItemClickListener) : RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    val films = mutableListOf<Film>()

    class FilmHolder(item : View) : RecyclerView.ViewHolder(item) {
        val binding = FilmItemBinding.bind(item)
        fun bind(film: Film)= with(binding){
            filmName.text = film.name
            filmDesc.text = film.desc
            filmPoster.setImageResource(film.posterId)
        }
    }

    interface OnItemClickListener{
        fun onClik()
    }

    fun addFilms(newFilms: ArrayList<Film>){
        films.clear()
        films.addAll(newFilms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        return FilmHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int {
        return films.size
    }


}