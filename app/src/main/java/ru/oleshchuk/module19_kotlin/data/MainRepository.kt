package ru.oleshchuk.module19_kotlin.data

import javax.inject.Inject

interface BaseRepository

class MainRepository @Inject constructor() : BaseRepository{
}