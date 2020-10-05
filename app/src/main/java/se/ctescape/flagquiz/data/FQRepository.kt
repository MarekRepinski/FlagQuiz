package se.ctescape.flagquiz.data

class FQRepository(private val fQdao: FQdao) {
    suspend fun getAllData(): List<FQdata>{
        return fQdao.readAllData()
    }

    suspend fun addFQdata(fQdata: FQdata) {
        fQdao.addFQdata(fQdata)
    }

    suspend fun updateFQdata(fQdata: FQdata) {
        fQdao.updateFQdata(fQdata)
    }

    suspend fun deleteFQdata(fQdata: FQdata) {
        fQdao.deleteFQdata(fQdata)
    }

    suspend fun deleteAllFQdata() {
        fQdao.deleteAllFQdata()
    }

    suspend fun getCount(): Int {
        return fQdao.getCount()
    }

    suspend fun getContinent(continent: String): List<FQdata>{
        return fQdao.getContinent(continent)
    }
}