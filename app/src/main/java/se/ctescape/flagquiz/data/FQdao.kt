package se.ctescape.flagquiz.data

import androidx.room.*

@Dao
interface FQdao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFQdata(fqData: FQdata)

    @Update
    suspend fun updateFQdata(fqData: FQdata)

    @Delete
    suspend fun deleteFQdata(fqData: FQdata)

    @Query("DELETE FROM fq_main_table")
    suspend fun deleteAllFQdata()

    @Query("SELECT * FROM fq_main_table ORDER BY id ASC")
    suspend fun readAllData(): List<FQdata>

    @Query("SELECT COUNT(*) FROM fq_main_table")
    suspend fun getCount(): Int

    @Query("SELECT * FROM fq_main_table WHERE continent LIKE :continent")
    suspend fun getContinent(continent: String): List<FQdata>
}