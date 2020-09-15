package se.ctescape.flagquiz

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.*

class GameActivity : AppCompatActivity() {
    val gameImageViews = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var id = 0
        Log.d("AIK","id = $id")
        for(i in 0..3){
            val tempStr = "ivFlagg${i.toString()}"
            id = getResources().getIdentifier("ivFlagg${i.toString()}", "id", getPackageName())
            Log.d("AIK","id = $id")
            gameImageViews.add(findViewById<ImageView>(id))
            gameImageViews[i].setOnTouchListener { v, e ->
                getAnswer(v, e, gameImageViews[i],i)
                true
            }
        }

        val noOfBools = intent.getIntExtra("noOfBools",0)
        if (noOfBools > 0) {
            val areas = BooleanArray(intent.getIntExtra("noOfBools", 0))
            for (i in 0..(noOfBools - 1)) {
                areas[i] = intent.getBooleanExtra("bool$i", false)
            }
            if (areas[1])
                areas[0] = true
//            val flagQuizGame = FlagQuiz(areas)
        }
    }

    fun getAnswer(v: View, e : MotionEvent, iv : ImageView, i : Int){
//        val defaultImage: Drawable = drawable
        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("AIK","Down")
                iv.setImageResource(R.drawable.smileyworried)
            }
            MotionEvent.ACTION_UP -> {
                Log.d("AIK","Up - $i")
                if (i == 1) {
                    Log.d("AIK", "Up - wrong")
                    iv.setImageResource(R.drawable.wrong)
                }
                else {
                    Log.d("AIK", "Up - correct")
                    iv.setImageResource(R.drawable.correct)
                }
//                Detta funkar!
                Handler().postDelayed({
                    Log.d("AIK","in timer")
                    resetImages()
                },3000)

//                Läs på mer om Corutiner
//                Timer().schedule(3000){
//                    Log.d("AIK","in timer")
//                    resetImages()
//                }
                Log.d("AIK","after timer")
            }
        }
    }

    suspend fun resetImages(){
        delay(3000)
        Log.d("AIK","reset")
        gameImageViews[0].setImageResource(R.drawable.se)
        gameImageViews[1].setImageResource(R.drawable.no)
        gameImageViews[2].setImageResource(R.drawable.fi)
        gameImageViews[3].setImageResource(R.drawable.dk)
    }

}