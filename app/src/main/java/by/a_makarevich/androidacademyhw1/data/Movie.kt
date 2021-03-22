package by.a_makarevich.androidacademyhw1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "moviesTable")
data class Movie(
    @PrimaryKey (autoGenerate = false)
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: String,
    val runtime: Int,
    @TypeConverters(GenreConverter::class)
    val genres: List<Genre>,
    val actors: List<Actor>
)

@Entity
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val repoId: Int,
    val prefKey: Int?,
    val nextKey: Int?
)

class GenreConverter {
    @TypeConverter
    fun fromListGenre(list: List<Genre>) : String {
        val s: StringBuilder = java.lang.StringBuilder()
        list.forEach { s.append(it.id).append(",").append(it.name).append(";") }
        return s.toString()
    }
    @TypeConverter
    fun toListGenre(s: String) : List<Genre> {
        val genres = arrayListOf<Genre>()
        val subString: java.lang.StringBuilder = java.lang.StringBuilder()
        val tempListSubStrings = arrayListOf<StringBuilder>()
        s.forEach { c ->
            if (c.equals(";")) {
                subString.append(c)
                tempListSubStrings.add(subString)
            }
                else subString.append(c)
        }

        val genre: Genre = Genre(0, "")
        tempListSubStrings.forEach { stringBuilder ->
            val s: java.lang.StringBuilder = java.lang.StringBuilder()
            stringBuilder.forEach {
                if (it.equals(",")) {
                    genre.id = s.toString().toInt()
                    s.clear()
                }
                if (it.equals(";")) {
                    genre.name = s.toString()
                }
            }
        }
        genres.add(genre)
        return genres
    }
}
