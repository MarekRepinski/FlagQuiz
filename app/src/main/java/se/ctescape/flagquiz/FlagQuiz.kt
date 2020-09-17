package se.ctescape.flagquiz

import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FlagQuiz (difficulty: BooleanArray){
    private val flagLista = mutableListOf<Flag>()
    var rond = 0
    var points = 0
    var correctAnswer = 0

    init {
        var cnt = 0
        if (difficulty[1])
            difficulty[0] = true //Norden tillhör Europa
        for (i in difficulty) {
            cnt++
            if (i)
                setFlags(cnt)
        }
    }

    fun printFlags() : Array<String> {
        rond++
        val dispLista = mutableListOf<Flag?>()
        var f: Flag

        for (i in 0..3) {
            dispLista.add(null)
        }

        //Hitta flagga som ska gissas
        do {
            f = flagLista[(0..(flagLista.size - 1)).random()]
        } while (f.used)
        f.used = true

        //Positionera flagga som ska gissas
        correctAnswer = (0..3).random()
        dispLista[correctAnswer] = f

        //Fyll på med övriga flaggor
        for (i in 0..3) {
            if (dispLista[i] == null) {
                dispLista[i] = uniqueFlag(dispLista)
            }
        }
        return arrayOf(dispLista[0]!!.flagId, dispLista[1]!!.flagId, dispLista[2]!!.flagId, dispLista[3]!!.flagId, dispLista[correctAnswer]!!.country)
    }

    fun uniqueFlag(l: List<Flag?>): Flag {
        var f: Flag
        var foundIt = false
        do {
            f = flagLista[(0..(flagLista.size - 1)).random()]
            foundIt = true
            for (i in 0..3) {
                if (f == l[i])
                    foundIt = false
            }
        } while (!foundIt)

        return f
    }

    fun checkFlagsLeft(): Boolean {
        for (f in flagLista) {
            if (!f.used)
                return true
        }
        return false
    }

    private fun setFlags(i: Int) {
        //TODO: Titta på android room database
        when (i) {
            1 -> {
                flagLista.add(Flag("Sverige", "se"))
                flagLista.add(Flag("Norge", "no"))
                flagLista.add(Flag("Danmark", "dk"))
                flagLista.add(Flag("Finland", "fi"))
                flagLista.add(Flag("Island", "is"))
            }
            2 -> {
                flagLista.add(Flag("Albanien", "al"))
                flagLista.add(Flag("Andorra", "ad"))
                flagLista.add(Flag("Belgien", "be"))
                flagLista.add(Flag("Bosnien", "ba"))
                flagLista.add(Flag("Bulgarien", "bg"))
                flagLista.add(Flag("Estland", "ee"))
                flagLista.add(Flag("Frankrike", "fr"))
                flagLista.add(Flag("Georgien", "ge"))
                flagLista.add(Flag("Grekland", "gr"))
                flagLista.add(Flag("Irland", "ie"))
                flagLista.add(Flag("Italien", "it"))
                flagLista.add(Flag("Kroatien", "hr"))
                flagLista.add(Flag("Lettland", "lv"))
                flagLista.add(Flag("Liechtenstein", "li"))
                flagLista.add(Flag("Litauen", "lt"))
                flagLista.add(Flag("Luxemburg", "lu"))
                flagLista.add(Flag("Malta", "mt"))
                flagLista.add(Flag("Moldavien", "md"))
                flagLista.add(Flag("Monaco", "mc"))
                flagLista.add(Flag("Montenegro", "me"))
                flagLista.add(Flag("Nederländerna", "nl"))
                flagLista.add(Flag("Nordmakedonien", "mk"))
                flagLista.add(Flag("Polen", "pl"))
                flagLista.add(Flag("Portugal", "pt"))
                flagLista.add(Flag("Rumänien", "ro"))
                flagLista.add(Flag("Ryssland", "ru"))
                flagLista.add(Flag("San Marino", "sm"))
                flagLista.add(Flag("Schweiz", "ch"))
                flagLista.add(Flag("Serbien", "rs"))
                flagLista.add(Flag("Slovakien", "sk"))
                flagLista.add(Flag("Slovenien", "si"))
                flagLista.add(Flag("Spanien", "es"))
                flagLista.add(Flag("Storbritannien", "gb"))
                flagLista.add(Flag("Tjeckien", "cz"))
                flagLista.add(Flag("Tyskland", "de"))
                flagLista.add(Flag("Ukraina", "ua"))
                flagLista.add(Flag("Ungern", "hu"))
                flagLista.add(Flag("Vatikanstaten", "va"))
                flagLista.add(Flag("Vitryssland", "by"))
                flagLista.add(Flag("Österrike", "at"))
            }
        }
    }

    fun noOfFlags(): Int{
        return flagLista.size
    }
}