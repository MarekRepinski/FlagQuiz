package se.ctescape.flagquiz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_practice.*
import kotlinx.android.synthetic.main.activity_practice.view.*
import se.ctescape.flagquiz.DataManager.flagLista
import se.ctescape.flagquiz.R
import java.util.*

class PracticeActivity : AppCompatActivity() {
    var countryTxt = ""
    var lastFlag = -1

    //Counter used to display correct answer for 2 sek
    private val timerNew = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            printNewFlag()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        //Listener for the "Back" button
        back_btn.setOnClickListener {
            this.onBackPressed()
        }

        //Listener if someone click on the flag for 2 sek
        practice_flagg_iv.setOnTouchListener { view, motionEvent ->
            practice_answer_txt.text = countryTxt
            timerNew.start()
            true
        }
        printNewFlag()
    }

    //Choose a random flag from datamanager
    fun printNewFlag(){
        practice_answer_txt.text = "?????"
        var loopLimit = 0
        var i : Int
        do {
            i = (0..(flagLista.size - 1)).random()
            loopLimit++
        } while(i == lastFlag && loopLimit < 1000)

        lastFlag = i
        val f = flagLista[i]
        if (Locale.getDefault().language == "pl"){
            countryTxt = f.plCountry
        } else if (Locale.getDefault().language == "sv"){
            countryTxt = f.svCountry
        } else {
            countryTxt = f.enCountry
        }
        practice_flagg_iv.setImageResource(
            resources.getIdentifier(
                f.flagId,
                "drawable",
                packageName
            )
        )
    }
}