package ru.oleshchuk.module19_kotlin.domain

import ru.oleshchuk.module19_kotlin.data.MainRepository

class Interactor(private val mainRepo: MainRepository) {
    fun getFilmsDb() : List<Film> = mainRepo.films
}