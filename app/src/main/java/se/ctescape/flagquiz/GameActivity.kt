package se.ctescape.flagquiz

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var flagQuizGame: FlagQuiz
    var onlyOnePick = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var id = 0
//        Log.d("AIK", "id = $id")
        for (i in 0..3) {
            val tempStr = "ivFlagg$i"
//            Log.d("AIK", "id = $id")
            id = resources.getIdentifier("ivFlagg$i", "id", packageName)
//            Log.d("AIK", "id = $id")
            gameImageViews.add(findViewById<ImageView>(id))
            gameImageViews[i].setOnTouchListener { v, e ->
                getAnswer(v, e, gameImageViews[i], i)
                true
            }
        }

        val noOfBools = intent.getIntExtra("noOfBools", 0)
        if (noOfBools > 0) {
            val areas = BooleanArray(intent.getIntExtra("noOfBools", 0))
            for (i in 0..(noOfBools - 1)) {
                areas[i] = intent.getBooleanExtra("bool$i", false)
            }
            if (areas[1])
                areas[0] = true
            flagQuizGame = FlagQuiz(areas)
            if (!flagQuizGame.checkFlagsLeft())
                endGame()
            else
                printNewFlags()
        }
    }

    fun printNewFlags() {
        val idArray = flagQuizGame.printFlags()
        tvGameHeader.text = getString(
            R.string.questNo,
            flagQuizGame.rond.toString(),
            flagQuizGame.noOfFlags().toString()
        )
        for (i in 0..(gameImageViews.size - 1)) {
//            Log.d("AIK",idArray[i])
            gameImageViews[i].setImageResource(
                resources.getIdentifier(
                    idArray[i],
                    "drawable",
                    packageName
                )
            )
        }
        tvGameQuestion.text = getString(R.string.gameQuestion, idArray[gameImageViews.size])
        onlyOnePick = true
    }

    fun endGame() {
        Toast.makeText(this, "Game ended", Toast.LENGTH_SHORT).show()
    }

    fun getAnswer(v: View, e: MotionEvent, iv: ImageView, i: Int) {
        if (onlyOnePick) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
//                    Log.d("AIK", "Down")
                    iv.setImageResource(R.drawable.smileyworried)
                }
                MotionEvent.ACTION_UP -> {
                    onlyOnePick = false
//                    Log.d("AIK", "Up - $i right answer = ${flagQuizGame.correctAnswer}")
                    if (i != flagQuizGame.correctAnswer) {
//                        Log.d("AIK", "Up - wrong")
                        iv.setImageResource(R.drawable.wrong)
                    } else {
//                        Log.d("AIK", "Up - correct")
                        iv.setImageResource(R.drawable.correct)
                    }
//                TODO: Detta funkar! dålig lösning. Titta på Coroutines
                    Handler().postDelayed({
//                        Log.d("AIK", "in timer")
                        if (i != flagQuizGame.correctAnswer || !flagQuizGame.checkFlagsLeft())
                            endGame()
                        else
                            printNewFlags()
                    }, 2000)
//                Funkar inte!!
//                GlobalScope.launch { // launch a new coroutine in background and continue
//                    delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
//                    CoroutineScope(Dispatchers.Main).launch {
//                        Log.d("AIK", "in timer")
//                        resetImages()
//                    }
//                }
//                Funkar inte!!
//                Timer().schedule(3000){
//                    Log.d("AIK","in timer")
//                    resetImages()
//                }
//                    Log.d("AIK", "after timer")
                }
            }
        }
    }
}