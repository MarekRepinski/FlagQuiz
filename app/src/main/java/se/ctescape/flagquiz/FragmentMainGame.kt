package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

//private const val ARG_PARAM1 = "param1"

class FragmentMainGame : Fragment() {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var flagQuizGame: FlagQuiz
    var onlyOnePick = true
    lateinit var rubrik : TextView
    lateinit var question : TextView
    private lateinit var listener : onEndOfGame
    private val PROGRESS_MAX = 100
    private val PROGRESS_START = 0
    private val JOB_TIME = 10000 //ms
    private lateinit var progressBar: ProgressBar
    private lateinit var job: CompletableJob

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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            areas = it.getBooleanArray(ARG_PARAM1)?:areaTemp
//        }
//    }
//
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_game, container, false)
        progressBar = v.findViewById(R.id.progressBar)

        rubrik = v.findViewById(R.id.tvGameHeader)
        question = v.findViewById(R.id.tvGameQuestion)
        for (i in 0..3) {
            gameImageViews.add(v.findViewById<ImageView>(resources.getIdentifier("ivFlagg$i", "id", activity!!.packageName)))
            gameImageViews[i].setOnTouchListener { view, e ->
                getAnswer(e, gameImageViews[i], i)
                true
            }
        }
        flagQuizGame = FlagQuiz()
        if (!flagQuizGame.checkFlagsLeft())
            endGame()
        else
            printNewFlags()
        return v
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: BooleanArray) =
//            FragmentMainGame().apply {
//                arguments = Bundle().apply {
//                    putBooleanArray(ARG_PARAM1, param1)
//                }
//            }
//    }

    fun ProgressBar.startJobOrCancel(job: Job){
        if (this.progress > 0){
            /* no-op */
        } else {
            //IO + job skapar ett nytt unikt context
            CoroutineScope(Dispatchers.IO + job).launch{
                for(i in PROGRESS_START..PROGRESS_MAX){
                    delay((JOB_TIME/PROGRESS_MAX).toLong())
                    this@startJobOrCancel.progress = i
                }
                GlobalScope.launch(Main){
                    for(i in 0..3){
                        gameImageViews[i].setImageResource(R.drawable.timeisup)
                    }
                    timerEnd.start()                }
//                showToast("Times Up!")
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    fun printNewFlags() {
        initJob()
        progressBar.startJobOrCancel(job)

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

    private fun initJob() {
        job = Job()
        // När jobbet avslutas kommer koden nedan att köras (även vid cancel)
//        job.invokeOnCompletion {
//            it?.message.let {
//                var msg = it
//                if (msg.isNullOrBlank()){
//                    msg = "Unknown cancellation error."
//                }
//            }
//        }
        progressBar.max = PROGRESS_MAX
        progressBar.progress = PROGRESS_START
    }

//    fun showToast(msg: String){
//        GlobalScope.launch(Dispatchers.Main) {
//            Toast.makeText(
//                this@MainActivity,
//                msg,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }

    fun endGame() {
        listener.onEndOfGame(flagQuizGame.points, flagQuizGame.noOfFlags())
    }

    fun getAnswer(e: MotionEvent, iv: ImageView, i: Int) {
        if (onlyOnePick) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    iv.setImageResource(R.drawable.smileyworried)
                }
                MotionEvent.ACTION_UP -> {
                    onlyOnePick = false
                    job.cancel(CancellationException("Resetting timer"))
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