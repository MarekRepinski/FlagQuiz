package se.ctescape.flagquiz.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FillDataBase(val context: Context) {
    private val repository : FQRepository
    var finished = false

    init {
        val dao = FQdatabase.getDatabase(context).FQdao()
        repository = FQRepository(dao)
        println("AIK!! Filling Database")
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAllFQdata()
            var tempInt = repository.getCount()
            println("AIK!! getCount() = ${tempInt}")
            if (tempInt == 0){
                var tempFQ = FQdata(0, "Sweden", "Sverige", "Szwecja", "se", "scandinavia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Norway", "Norge", "Norwegia", "no", "scandinavia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Denmark", "Danmark", "Dania", "dk", "scandinavia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Finland", "Finland", "Finlandia", "fi", "scandinavia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Iceland", "Island", "Islandia", "is", "scandinavia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Albania", "Albanien", "Albania", "al", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Andorra", "Andorra", "Andora", "ad", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Belgium", "Belgien", "Belgia", "be", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bosnia", "Bosnien", "Bośnia", "ba", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bulgaria", "Bulgarien", "Bułgaria", "bg", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Estonia", "Estland", "Estonia", "ee", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"France", "Frankrike", "Francja", "fr", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Georgia", "Georgien", "Gruzja", "ge", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Greece", "Grekland", "Grecja", "gr", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ireland", "Irland", "Irlandia", "ie", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Italy", "Italien", "Włochy", "it", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Croatia", "Kroatien", "Chorwacja", "hr", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Latvia", "Lettland", "Łotwa", "lv", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Liechtenstein", "Liechtenstein", "Liechtenstein", "li", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Lithuania", "Litauen", "Litwa", "lt", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Luxembourg", "Luxemburg", "Luksemburg", "lu", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Malta", "Malta", "Malta", "mt", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Moldova", "Moldavien", "Moldova", "md", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Monaco", "Monaco", "Monako", "mc", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Montenegro", "Montenegro", "Czarnogóra", "me", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Netherlands", "Nederländerna", "Holandia", "nl", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Northern Macedonia", "Nordmakedonien", "Macedonia Północna", "mk", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Poland", "Polen", "Polska", "pl", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Portugal", "Portugal", "Portugalia", "pt", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Romania", "Rumänien", "Rumunia", "ro", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Russia", "Ryssland", "Rosja", "ru", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"San Marino", "San Marino", "San Marino", "sm", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Switzerland", "Schweiz", "Szwajcaria", "ch", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Serbia", "Serbien", "Serbia", "rs", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Slovakia", "Slovakien", "Słowacja", "sk", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Slovenia", "Slovenien", "Słowenia", "si", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Spain", "Spanien", "Hiszpania", "es", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Great Britain", "Storbritannien", "Britania", "gb", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Czech Republic", "Tjeckien", "Republika Czeska", "cz", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Germany", "Tyskland", "Niemcy", "de", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ukraine", "Ukraina", "Ukraina", "ua", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Hungary", "Ungern", "Węgry", "hu", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Vatican City State", "Vatikanstaten", "Państwo Watykańskie", "va", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Belarus", "Vitryssland", "Białoruś", "by", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Austria", "Österrike", "Austria", "at", "europe")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Algeria", "Algeriet", "Algieria", "dz", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Angola", "Angola", "Angola", "ao", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Benin", "Benin", "Benin", "bj", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Botswana", "Botswana", "Botswana", "bw", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Burkina Faso", "Burkina Faso", "Burkina Faso", "bf", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Burundi", "Burundi", "Burundi", "bi", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Central African Republic", "Centralafrikanska republiken", "Republika Środkowoafrykańska", "cf", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Djibouti", "Djibouti", "Dżibuti", "dj", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Egypt", "Egypten", "Egipt", "eg", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Equatorial Guinea", "Ekvatorialguinea", "Gwinea Równikowa", "gq", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ivory Coast", "Elfenbenskusten", "Wybrzeże Kości Słoniowej", "ci", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Eritrea", "Eritrea", "Erytrea", "er", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ethiopia", "Etiopien", "Etiopia", "et", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Gabon", "Gabon", "Gabon", "ga", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"The Gambia", "Gambia", "Gambia", "gm", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ghana", "Ghana", "Ghana", "gh", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Guinea", "Guinea", "Gwinea", "gn", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Guinea-Bissau", "Guinea-Bissau", "Gwinea Bissau", "gw", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Cameroon", "Kamerun", "Kamerun", "cm", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Cape Verde", "Kap Verde", "Republika Zielonego Przylądka", "cv", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Kenya", "Kenya", "Kenia", "ke", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Comoros", "Komorerna", "Komory", "km", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Congo-Brazzaville", "Kongo-Brazzaville", "Kongo", "cg", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Congo-Kinshasa", "Kongo-Kinshasa", "Demokratyczna Republika Konga", "cd", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Lesotho", "Lesotho", "Lesotho", "ls", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Liberia", "Liberia", "Liberia", "lr", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Libya", "Libyen", "Libia", "ly", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Madagascar", "Madagaskar", "Madagaskar", "mg", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Malawi", "Malawi", "Malawi", "mw", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mali", "Mali", "Mali", "ml", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Morocco", "Marocko", "Maroko", "ma", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mauritania", "Mauretanien", "Mauretania", "mr", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mauritius", "Mauritius", "Mauritius", "mu", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mozambique", "Moçambique", "Mozambik", "mz", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Namibia", "Namibia", "Namibia", "na", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Niger", "Niger", "Niger", "ne", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Nigeria", "Nigeria", "Nigeria", "ng", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Rwanda", "Rwanda", "Ruanda", "rw", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"São Tomé and Príncipe", "São Tomé och Príncipe", "Wyspy Świętego Tomasza i Książęca", "st", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Senegal", "Senegal", "Senegal", "sn", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Seychelles", "Seychellerna", "Seszele", "sc", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Sierra Leone", "Sierra Leone", "Sierra Leone", "sl", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Somalia", "Somalia", "Somalia", "so", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Sudan", "Sudan", "Sudan", "sd", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"South Africa", "Sydafrika", "Republika Południowej Afryki", "za", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"South Sudan", "Sydsudan", "Sudan Południowy", "ss", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Eswatini", "Swaziland", "Suazi", "sz", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Tanzania", "Tanzania", "Tanzania", "tz", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Chad", "Tchad", "Czad", "td", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Togo", "Togo", "Togo", "tg", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Tunisia", "Tunisien", "Tunezja", "tn", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Uganda", "Uganda", "Uganda", "ug", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Zambia", "Zambia", "Zambia", "zm", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Zimbabwe", "Zimbabwe", "Zimbabwe", "zw", "africa")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Afghanistan", "Afghanistan", "Afganistan", "af", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Armenia", "Armenien", "Armenia", "am", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Azerbaijan", "Azerbajdzjan", "Azerbejdżan", "az", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bahrain", "Bahrain", "Bahrajn", "bh", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bangladesh", "Bangladesh", "Bangladesz", "bd", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bhutan", "Bhutan", "Bhutan", "bt", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Brunei", "Brunei", "Brunei", "bn", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Cyprus", "Cypern", "Cypr", "cy", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Philippines", "Filippinerna", "Filipiny", "ph", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"United Arab Emirates", "Förenade Arabemiraten", "Zjednoczone Emiraty Arabskie", "ae", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"India", "Indien", "Indie", "in", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Indonesia", "Indonesien", "Indonezja", "id", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Iraq", "Irak", "Irak", "iq", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Iran", "Iran", "Iran", "ir", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Israel", "Israel", "Izrael", "il", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Japan", "Japan", "Japonia", "jp", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Yemen", "Jemen", "Jemen", "ye", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Jordan", "Jordanien", "Jordania", "jo", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Cambodia", "Kambodja", "Kambodża", "kh", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Kazakhstan", "Kazakstan", "Kazachstan", "kz", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"China", "Kina", "Chiny", "cn", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Kyrgyzstan", "Kirgizistan", "Kirgistan", "kg", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Kuwait", "Kuwait", "Kuwejt", "kw", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Laos", "Laos", "Laos", "la", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Lebanon", "Libanon", "Liban", "lb", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Malaysia", "Malaysia", "Malezja", "my", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Maldives", "Maldiverna", "Malediwy", "mv", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mongolia", "Mongoliet", "Mongolia", "mn", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Myanmar", "Myanmar", "Mjanma", "mm", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Nepal", "Nepal", "Nepal", "np", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"North Korea", "Nordkorea", "Korea Północna", "kp", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Oman", "Oman", "Oman", "om", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Pakistan", "Pakistan", "Pakistan", "pk", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Qatar", "Qatar", "Katar", "qa", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Saudi Arabia", "Saudiarabien", "Arabia Saudyjska", "sa", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Singapore", "Singapore", "Singapur", "sg", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Sri Lanka", "Sri Lanka", "Sri Lanka", "lk", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"South Korea", "Sydkorea", "Korea Południowa", "kr", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Syria", "Syrien", "Syria", "sy", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Tajikistan", "Tadzjikistan", "Tadżykistan", "tj", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Taiwan", "Taiwan", "Republika Chińska", "tw", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Thailand", "Thailand", "Tajlandia", "th", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Turkey", "Turkiet", "Turcja", "tr", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Turkmenistan", "Turkmenistan", "Turkmenistan", "tm", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Uzbekistan", "Uzbekistan", "Uzbekistan", "uz", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Vietnam", "Vietnam", "Wietnam", "vn", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"East Timor", "Östtimor", "Timor Wschodni", "tl", "asia")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Antigua and Barbuda", "Antigua och Barbuda", "Antigua i Barbuda", "ag", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bahamas", "Bahamas", "Bahamy", "bs", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Barbados", "Barbados", "Barbados", "bb", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Belize", "Belize", "Belize", "bz", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Costa Rica", "Costa Rica", "Kostaryka", "cr", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Dominica", "Dominica", "Dominika", "dm", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Dominican Republic", "Dominikanska republiken", "Dominikana", "dom", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"El Salvador", "El Salvador", "Salwador", "sv", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Grenada", "Grenada", "Grenada", "gd", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Guatemala", "Guatemala", "Gwatemala", "gt", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Haiti", "Haiti", "Haiti", "ht", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Honduras", "Honduras", "Honduras", "hn", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Jamaica", "Jamaica", "Jamajka", "jm", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Canada", "Kanada", "Kanada", "ca", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Cuba", "Kuba", "Kuba", "cu", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Mexico", "Mexiko", "Meksyk", "mx", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Nicaragua", "Nicaragua", "Nikaragua", "ni", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Panama", "Panama", "Panama", "pa", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Saint Kitts and Nevis", "Saint Kitts och Nevis", "Saint Kitts i Nevis", "kn", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Saint Lucia", "Saint Lucia", "Saint Lucia", "lc", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Saint Vincent and the Grenadines", "Saint Vincent och Grenadinerna ", "Saint Vincent i Grenadyny", "vc", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Trinidad and Tobago", "Trinidad och Tobago", "Trynidad i Tobago", "tt", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"United States", "USA", "Stany Zjednoczone", "us", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Argentina", "Argentina", "Argentyna", "ar", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Bolivia", "Bolivia", "Boliwia", "bo", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Brazil", "Brasilien", "Brazylia", "br", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Chile", "Chile", "Chile", "cl", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Colombia", "Colombia", "Kolumbia", "co", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Ecuador", "Ecuador", "Ekwador", "ec", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Guyana", "Guyana", "Gujana", "gy", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Paraguay", "Paraguay", "Paragwaj", "py", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Peru", "Peru", " Peru", "pe", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Suriname", "Surinam", "Surinam", "sr", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Uruguay", "Uruguay", "Urugwaj", "uy", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Venezuela", "Venezuela", "Wenezuela", "ve", "america")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Australia", "Australien", "Australia", "au", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Fiji", "Fiji", "Fidżi", "fj", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Kiribati", "Kiribati", "Kiribati", "ki", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Marshall Islands", "Marshallöarna", "Wyspy Marshalla", "mh", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Federated States of Micronesia", "Mikronesiens federerade stater", "Mikronezja", "fm", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Nauru", "Nauru", "Nauru", "nr", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"New Zealand", "Nya Zeeland", "Nowa Zelandia", "nz", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Palau", "Palau", "Palau", "pw", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Papua New Guinea", "Papua Nya Guinea", "Papua-Nowa Gwinea", "pg", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Solomon Islands", "Salomonöarna", "Wyspy Salomona", "sb", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Samoa", "Samoa", "Samoa", "ws", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Tonga", "Tonga", "Tonga", "to", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Tuvalu", "Tuvalu", "Tuvalu", "tv", "oceania")
                repository.addFQdata(tempFQ)
                tempFQ = FQdata(0,"Vanuatu", "Vanuatu", "Vanuatu", "vu", "oceania")
                repository.addFQdata(tempFQ)
            }
            tempInt = repository.getCount()
            println("AIK!! getCount() = ${tempInt} - After")
            finished = true
        }
    }
}