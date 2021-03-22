package by.a_makarevich.androidacademyhw1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.a_makarevich.androidacademyhw1.data.GenreConverter
import by.a_makarevich.androidacademyhw1.data.Movie
import by.a_makarevich.androidacademyhw1.data.RemoteKeys


@Database(version = 1, entities = [Movie::class, RemoteKeys::class], exportSchema = false)
@TypeConverters(GenreConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun getMovieDao(): MovieDao

    companion object {
        val MOVIES_DB = "movies.db"

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MoviesDatabase::class.java, MOVIES_DB)
                .build()
    }
}