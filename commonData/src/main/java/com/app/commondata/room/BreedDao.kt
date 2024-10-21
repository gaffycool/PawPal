package com.app.commondata.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the BreedModel class.
 */
@Dao
interface BreedDao {
    @Query("SELECT * FROM breeds ORDER BY breed")
    fun getFavoriteBreeds(): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breedEntity: BreedEntity)

    @Delete
    suspend fun delete(vararg breedEntity: BreedEntity)

    @Query("SELECT * FROM breeds WHERE symbol = :symbol LIMIT 1")
    suspend fun findWatchlist(symbol: String): BreedEntity?
}
