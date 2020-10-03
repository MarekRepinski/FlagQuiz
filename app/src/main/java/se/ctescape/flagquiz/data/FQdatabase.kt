package se.ctescape.flagquiz.data

import android.content.Context
import androidx.room.*

@Database(entities = [FQdata::class], version = 1, exportSchema = false)
abstract class FQdatabase: RoomDatabase() {
    abstract fun FQdao(): FQdao

    companion object {
        @Volatile private var INSTANCE : FQdatabase? = null

        fun getDatabase(context: Context) : FQdatabase{
            val tempINSTANCE = INSTANCE
            if (tempINSTANCE != null){
                return tempINSTANCE
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FQdatabase::class.java,
                    "fq_main_database"
                ).build()
                INSTANCE = instance
                return  instance
            }

        }
    }
}