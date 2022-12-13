package ru.oleshchuk.module19_kotlin.constants

import ru.oleshchuk.module19_kotlin.R
import ru.oleshchuk.module19_kotlin.model.Film

object FilmBd {
    val films = arrayListOf<Film>(
        Film(
            "Доктор Ноу (фильм, 1962)  Dr. No",
            R.drawable.film1,
            "1962, боевик, приключения, триллер, Великобритания, США",
            55
        ),
        Film(
            "Лок (фильм, 2013)  Locke",
            R.drawable.film2,
            "2013, драма, Великобритания, США",
            70
        ),
        Film(
            "Мастер (фильм, 2012)",
            R.drawable.film3,
            "The Master 2012, драма, США",
            48
        ),
        Film(
            "Меланхолия (фильм, 2011)",
            R.drawable.film4,
            "Melancholia 2011, драма, фантастика, Дания, Швеция, Франция, Германия",
            84
        ),
        Film(
            "Темный рыцарь (фильм, 2008)",
            R.drawable.film5,
            "The Dark Knight 2008, боевик, драма, триллер, США, Великобритания",
            82
        ),
        Film(
            "Властелин Колец: Две Крепости (фильм, 2002)",
            R.drawable.film6,
            "The Lord of the Rings: The Two Towers 2002, боевик, драма, приключения, США, Новая Зеландия",
            57
        ),
        Film(
            "Интерстеллар (фильм, 2014)",
            R.drawable.film7,
            "Interstellar 2014, драма, приключения, фантастика, США, Великобритания, Канада",
            91
        ),
        Film(
            "Пианист (фильм, 2002)",
            R.drawable.film8,
            "The Pianist 2002, биографический, драма, музыка, Франция, Польша, Германия, Великобритания",
            65
        )
    )
}