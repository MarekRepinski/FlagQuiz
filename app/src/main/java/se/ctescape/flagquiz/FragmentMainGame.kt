package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main_game.*
import java.lang.ClassCastException

private const val ARG_PARAM1 = "param1"

class FragmentMainGame : Fragment() {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var flagQuizGame: FlagQuiz
    var onlyOnePick = true
    private lateinit var areas: BooleanArray
    lateinit var rubrik : TextView
    lateinit var question : TextView
    private var areaTemp = BooleanArray(0) //Dirty solution... Ask for help
    private lateinit var listener : onEndOfGame

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onEndOfGame)
            listener = context
        else
            throw ClassCastException(context.toString() + " must be onEndOfGame")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            areas = it.getBooleanArray(ARG_PARAM1)?:areaTemp
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_game, container, false)
        rubrik = v.findViewById(R.id.tvGameHeader)
        question = v.findViewById(R.id.tvGameQuestion)
        var id = 0
        for (i in 0..3) {
            val tempStr = "ivFlagg$i"
            id = resources.getIdentifier("ivFlagg$i", "id", activity!!.packageName)
//            Log.d("AIK",id.toString())
            gameImageViews.add(v.findViewById<ImageView>(id))
            gameImageViews[i].setOnTouchListener { v, e ->
                getAnswer(v, e, gameImageViews[i], i)
                true
            }
        }
        flagQuizGame = FlagQuiz(areas)
        if (!flagQuizGame.checkFlagsLeft())
            endGame()
        else
//            Log.d("AIK","${R.string.questNo}, ${this.flagQuizGame.rond.toString()}, ${this.flagQuizGame.noOfFlags().toString()}")
            printNewFlags()
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: BooleanArray) =
            FragmentMainGame().apply {
                arguments = Bundle().apply {
                    putBooleanArray(ARG_PARAM1, param1)
                }
            }
    }

    @SuppressLint("StringFormatInvalid")
    fun printNewFlags() {
        val idArray = flagQuizGame.printFlags()
        rubrik.text = activity!!.getString(
            R.string.questNo,
            this.flagQuizGame.rond.toString(),
            this.flagQuizGame.noOfFlags().toString()
        )
        for (i in 0..(gameImageViews.size - 1)) {
//            Log.d("AIK",idArray[i])
            gameImageViews[i].setImageResource(
                resources.getIdentifier(
                    idArray[i],
                    "drawable",
                    activity!!.packageName
                )
            )
        }
        question.text = getString(R.string.gameQuestion, idArray[gameImageViews.size])
        onlyOnePick = true
    }

    fun endGame() {
        Log.d("AIK","Listener - $flagQuizGame.points")
        listener.onEndOfGame(flagQuizGame.points)
        Toast.makeText(activity, "Game ended", Toast.LENGTH_SHORT).show()
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
                        flagQuizGame.points++
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

    interface onEndOfGame{
        fun onEndOfGame(points : Int)
    }
}