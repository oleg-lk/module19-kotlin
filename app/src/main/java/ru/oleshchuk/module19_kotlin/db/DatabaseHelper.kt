package ru.oleshchuk.module19_kotlin.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Optional
import javax.inject.Inject

class DatabaseHelper @Inject constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT UNIQUE, " +
                "$COLUMN_POSTER TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_RATING REAL)" )
    }

    //Миграций мы не предполагаем, поэтому метод пустой
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    companion object{

        private fun getColumn(cursor: Cursor, colName: String) : Optional<Any>{
            val ind = cursor.getColumnIndex(colName)
            if (ind == -1){
                return Optional.empty()
            }
            return when(colName){
                COLUMN_TITLE-> Optional.of(cursor.getString(ind))
                COLUMN_POSTER-> Optional.of(cursor.getString(ind))
                COLUMN_DESCRIPTION-> Optional.of(cursor.getString(ind))
                COLUMN_RATING-> Optional.of(cursor.getDouble(ind))
                else->Optional.empty()
            }
        }
        fun getTitle(cursor: Cursor) : String{
            return getColumn(cursor, COLUMN_TITLE).orElse("") as String
        }
        fun getPoster(cursor: Cursor) : String{
            return getColumn(cursor, COLUMN_POSTER).orElse("") as String
        }
        fun getDescription(cursor: Cursor) : String{
            return getColumn(cursor, COLUMN_DESCRIPTION).orElse("") as String
        }
        fun getRating(cursor: Cursor) : Double{
            return getColumn(cursor, COLUMN_RATING).orElse(0) as Double
        }

        //Название самой БД
        const val DATABASE_NAME = "films.db"
        //Версия БД
        const val DATABASE_VERSION = 1

        //Константы для работы с таблицей, они нам понадобятся в CRUD операциях и,
        //возможно, в составлении запросов
        const val TABLE_NAME = "films_table"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_POSTER = "poster_path"
        const val COLUMN_DESCRIPTION = "overview"
        const val COLUMN_RATING = "vote_average"
    }

}