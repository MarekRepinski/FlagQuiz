package se.ctescape.flagquiz

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FlagQuiz (difficulty: BooleanArray, displFlags : List<ImageView>) : AppCompatActivity() {
    private val flagLista = mutableListOf<Flag>()
    var points = 0

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

    fun run() {
        var runOn = true
        while (checkFlagsLeft() && runOn) {
            runOn = rond()
            if (runOn)
                points++
        }
    }

    fun rond(): Boolean {
        val dispLista = mutableListOf<Flag?>()
        var f: Flag
        var rightAnswer: Int

        for (i in 0..3) {
            dispLista.add(null)
        }

        //Hitta flagga som ska gissas
        do {
            f = flagLista[(0..(flagLista.size - 1)).random()]
        } while (f.used)
        f.used = true

        //Positionera flagga som ska gissas
        rightAnswer = (0..3).random()
        dispLista[rightAnswer] = f

        //Fyll på med övriga flaggor
        for (i in 0..3) {
            if (dispLista[i] == null)
                dispLista[i] = uniqueFlag(dispLista)
        }

        return ++rightAnswer == (printFlags(rightAnswer, dispLista))?.toIntOrNull()
    }

    fun printFlags(i: Int, l: List<Flag?>): String? {
//        println("1.${l[0]!!.flag}\t\t\t2.${l[1]!!.flag}")
//        println("3.${l[2]!!.flag}\t\t\t4.${l[3]!!.flag}")
//        println("\nVilken flagga tillhör ${l[i - 1]!!.country}")
//        print("\n\nDitt svar: ")
        return readLine()
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
                flagLista.add(Flag("Sverige", getFlagId("se")))
                flagLista.add(Flag("Norge", getFlagId("no")))
                flagLista.add(Flag("Danmark", getFlagId("dk")))
                flagLista.add(Flag("Finland", getFlagId("fi")))
                flagLista.add(Flag("Island", getFlagId("is")))
            }
            2 -> {
                flagLista.add(Flag("Albanien", getFlagId("al")))
                flagLista.add(Flag("Andorra", getFlagId("ad")))
                flagLista.add(Flag("Belgien", getFlagId("be")))
                flagLista.add(Flag("Bosnien", getFlagId("ba")))
                flagLista.add(Flag("Bulgarien", getFlagId("bg")))
                flagLista.add(Flag("Estland", getFlagId("ee")))
                flagLista.add(Flag("Frankrike", getFlagId("fr")))
                flagLista.add(Flag("Georgien", getFlagId("ge")))
                flagLista.add(Flag("Grekland", getFlagId("gr")))
                flagLista.add(Flag("Irland", getFlagId("ie")))
                flagLista.add(Flag("Italien", getFlagId("it")))
                flagLista.add(Flag("Kroatien", getFlagId("hr")))
                flagLista.add(Flag("Lettland", getFlagId("lv")))
                flagLista.add(Flag("Liechtenstein", getFlagId("li")))
                flagLista.add(Flag("Litauen", getFlagId("lt")))
                flagLista.add(Flag("Luxemburg", getFlagId("lu")))
                flagLista.add(Flag("Malta", getFlagId("mt")))
                flagLista.add(Flag("Moldavien", getFlagId("md")))
                flagLista.add(Flag("Monaco", getFlagId("mc")))
                flagLista.add(Flag("Montenegro", getFlagId("me")))
                flagLista.add(Flag("Nederländerna", getFlagId("nl")))
                flagLista.add(Flag("Nordmakedonien", getFlagId("mk")))
                flagLista.add(Flag("Polen", getFlagId("pl")))
                flagLista.add(Flag("Portugal", getFlagId("pt")))
                flagLista.add(Flag("Rumänien", getFlagId("ro")))
                flagLista.add(Flag("Ryssland", getFlagId("ru")))
                flagLista.add(Flag("San Marino", getFlagId("sm")))
                flagLista.add(Flag("Schweiz", getFlagId("ch")))
                flagLista.add(Flag("Serbien", getFlagId("rs")))
                flagLista.add(Flag("Slovakien", getFlagId("sk")))
                flagLista.add(Flag("Slovenien", getFlagId("si")))
                flagLista.add(Flag("Spanien", getFlagId("es")))
                flagLista.add(Flag("Storbritannien", getFlagId("uk")))
                flagLista.add(Flag("Tjeckien", getFlagId("cz")))
                flagLista.add(Flag("Tyskland", getFlagId("de")))
                flagLista.add(Flag("Ukraina", getFlagId("ua")))
                flagLista.add(Flag("Ungern", getFlagId("hu")))
                flagLista.add(Flag("Vatikanstaten", getFlagId("va")))
                flagLista.add(Flag("Vitryssland", getFlagId("by")))
                flagLista.add(Flag("Österrike", getFlagId("at")))
            }
        }
    }

    fun getFlagId(s : String) : Int{
        return resources.getIdentifier(s, "id", packageName)

    }
}