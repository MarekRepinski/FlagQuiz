package se.ctescape.flagquiz

import java.util.Locale.getDefault

class FlagQuiz(difficulty: BooleanArray) {
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

    fun printFlags(): Array<String> {
        rond++
        var country = ""
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
            if (dispLista[i] == null) dispLista[i] = uniqueFlag(dispLista)
        }
        if (getDefault().language == "pl"){
            country = dispLista[correctAnswer]!!.plCountry
        } else if (getDefault().language == "sv"){
            country = dispLista[correctAnswer]!!.svCountry
        } else {
            country = dispLista[correctAnswer]!!.enCountry
        }
        return arrayOf(
            dispLista[0]!!.flagId,
            dispLista[1]!!.flagId,
            dispLista[2]!!.flagId,
            dispLista[3]!!.flagId,
            country
        )
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
                flagLista.add(Flag("Sweden", "Sverige", "Szwecja", "se"))
                flagLista.add(Flag("Norway", "Norge", "Norwegia", "no"))
                flagLista.add(Flag("Denmark", "Danmark", "Dania", "dk"))
                flagLista.add(Flag("Finland", "Finland", "Finlandia", "fi"))
                flagLista.add(Flag("Iceland", "Island", "Islandia", "is"))
            }
            2 -> {
                flagLista.add(Flag("Albania", "Albanien", "Albania", "al"))
                flagLista.add(Flag("Andorra", "Andorra", "Andora", "ad"))
                flagLista.add(Flag("Belgium", "Belgien", "Belgia", "be"))
                flagLista.add(Flag("Bosnia", "Bosnien", "Bośnia", "ba"))
                flagLista.add(Flag("Bulgaria", "Bulgarien", "Bułgaria", "bg"))
                flagLista.add(Flag("Estonia", "Estland", "Estonia", "ee"))
                flagLista.add(Flag("France", "Frankrike", "Francja", "fr"))
                flagLista.add(Flag("Georgia", "Georgien", "Gruzja", "ge"))
                flagLista.add(Flag("Greece", "Grekland", "Grecja", "gr"))
                flagLista.add(Flag("Ireland", "Irland", "Irlandia", "ie"))
                flagLista.add(Flag("Italy", "Italien", "Włochy", "it"))
                flagLista.add(Flag("Croatia", "Kroatien", "Chorwacja", "hr"))
                flagLista.add(Flag("Latvia", "Lettland", "Łotwa", "lv"))
                flagLista.add(Flag("Liechtenstein", "Liechtenstein", "Liechtenstein", "li"))
                flagLista.add(Flag("Lithuania", "Litauen", "Litwa", "lt"))
                flagLista.add(Flag("Luxembourg", "Luxemburg", "Luksemburg", "lu"))
                flagLista.add(Flag("Malta", "Malta", "Malta", "mt"))
                flagLista.add(Flag("Moldova", "Moldavien", "Moldova", "md"))
                flagLista.add(Flag("Monaco", "Monaco", "Monako", "mc"))
                flagLista.add(Flag("Montenegro", "Montenegro", "Czarnogóra", "me"))
                flagLista.add(Flag("Netherlands", "Nederländerna", "Holandia", "nl"))
                flagLista.add(
                    Flag(
                        "Northern Macedonia",
                        "Nordmakedonien",
                        "Macedonia Północna",
                        "mk"
                    )
                )
                flagLista.add(Flag("Poland", "Polen", "Polska", "pl"))
                flagLista.add(Flag("Portugal", "Portugal", "Portugalia", "pt"))
                flagLista.add(Flag("Romania", "Rumänien", "Rumunia", "ro"))
                flagLista.add(Flag("Russia", "Ryssland", "Rosja", "ru"))
                flagLista.add(Flag("San Marino", "San Marino", "San Marino", "sm"))
                flagLista.add(Flag("Switzerland", "Schweiz", "Szwajcaria", "ch"))
                flagLista.add(Flag("Serbia", "Serbien", "Serbia", "rs"))
                flagLista.add(Flag("Slovakia", "Slovakien", "Słowacja", "sk"))
                flagLista.add(Flag("Slovenia", "Slovenien", "Słowenia", "si"))
                flagLista.add(Flag("Spain", "Spanien", "Hiszpania", "es"))
                flagLista.add(Flag("United Kingdom", "Storbritannien", "Britania", "uk"))
                flagLista.add(Flag("Great Britain", "Tjeckien", "Republika Czeska", "cz"))
                flagLista.add(Flag("Germany", "Tyskland", "Niemcy", "de"))
                flagLista.add(Flag("Ukraine", "Ukraina", "Ukraina", "ua"))
                flagLista.add(Flag("Hungary", "Ungern", "Węgry", "hu"))
                flagLista.add(
                    Flag(
                        "Vatican City State",
                        "Vatikanstaten",
                        "Państwo Watykańskie",
                        "va"
                    )
                )
                flagLista.add(Flag("Belarus", "Vitryssland", "Białoruś", "by"))
                flagLista.add(Flag("Austria", "Österrike", "Austria", "at"))
            }
        }
    }

    fun noOfFlags(): Int {
        return flagLista.size
    }
}