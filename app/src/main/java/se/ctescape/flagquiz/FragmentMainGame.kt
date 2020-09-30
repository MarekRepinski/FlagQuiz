package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"

class FragmentMainGame : Fragment() {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var flagQuizGame: FlagQuiz
    var onlyOnePick = true
    private lateinit var areas: BooleanArray
    lateinit var rubrik : TextView
    lateinit var question : TextView
    private var areaTemp = BooleanArray(0)
    private lateinit var listener : onEndOfGame
    private val timerNew = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            printNewFlags()
        }
    }

    private val timerEnd = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            endGame()
        }
    }

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
        for (i in 0..3) {
            gameImageViews.add(v.findViewById<ImageView>(resources.getIdentifier("ivFlagg$i", "id", activity!!.packageName)))
            gameImageViews[i].setOnTouchListener { v, e ->
                getAnswer(v, e, gameImageViews[i], i)
                true
            }
        }
        flagQuizGame = FlagQuiz(areas)
        if (!flagQuizGame.checkFlagsLeft())
            endGame()
        else
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
        listener.onEndOfGame(flagQuizGame.points, flagQuizGame.noOfFlags())
    }

    fun getAnswer(v: View, e: MotionEvent, iv: ImageView, i: Int) {
        if (onlyOnePick) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    iv.setImageResource(R.drawable.smileyworried)
                }
                MotionEvent.ACTION_UP -> {
                    onlyOnePick = false
                    if (i != flagQuizGame.correctAnswer) {
                        iv.setImageResource(R.drawable.wrong)
                    } else {
                        flagQuizGame.points++
                        iv.setImageResource(R.drawable.correct)
                    }
                    if (i != flagQuizGame.correctAnswer || !flagQuizGame.checkFlagsLeft())
                        timerEnd.start()
                    else
                        timerNew.start()
                }
            }
        }
    }

    interface onEndOfGame{
        fun onEndOfGame(points : Int, noOfFlags : Int)
    }
}