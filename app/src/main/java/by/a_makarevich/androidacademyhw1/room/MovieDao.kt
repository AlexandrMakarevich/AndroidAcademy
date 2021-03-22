package by.a_makarevich.androidacademyhw1.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.a_makarevich.androidacademyhw1.data.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM moviesTable")
    fun getAll(): PagingSource<Int, Movie>

    @Query("DELETE FROM moviesTable")
    suspend fun deleteAllMovies()
}