package se.ctescape.flagquiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fq_main_table")
data class FQdata(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val enCountry: String,
    val svCountry: String,
    val plCountry: String,
    val flagId: String,
    val continent: String
)
