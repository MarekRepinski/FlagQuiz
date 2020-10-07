package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class FragmentMainGame : Fragment() {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var flagQuizGame: FlagQuiz
    var onlyOnePick = false
    lateinit var rubrik : TextView
    lateinit var question : TextView
    private lateinit var listener : onEndOfGame
    private val PROGRESS_MAX = 100
    private val PROGRESS_START = 0
    private val JOB_TIME = 10000 //ms
    private lateinit var progressBar: ProgressBar
    private lateinit var job: CompletableJob
    private var soundPool: SoundPool? = null
    private var rightSnd = 0
    private var wrongSnd = 0
    private var hisocreSnd = 0
    private var tickTockSnd = 0
    private var tickTockId = 0

    private val timerNew = object: CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            printNewFlags()
        }
    }

    private val timerEnd = object: CountDownTimer(3000, 1000) {
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

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        rightSnd = soundPool!!.load(context, R.raw.right_beep, 2)
        wrongSnd = soundPool!!.load(context, R.raw.bad_beep, 2)
        hisocreSnd = soundPool!!.load(context, R.raw.fanfare_short, 1)
        tickTockSnd = soundPool!!.load(context, R.raw.tick_tock, 3)
        soundPool?.setOnLoadCompleteListener { soundPool, i, i2 ->
            tickTockId = soundPool?.play(tickTockSnd,1f,1f,0,-1,1f)?:0
        }
        soundPool?.pause(tickTockId)

        flagQuizGame = FlagQuiz()
        if (!flagQuizGame.checkFlagsLeft())
            endGame()
        else
            printNewFlags()
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }

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
                    onlyOnePick = false
                    for(i in 0..3){
                        if (i == flagQuizGame.correctAnswer){
                            gameImageViews[i].startAnimation(AnimationUtils.loadAnimation(context, R.anim.blink))
                        } else {
                            gameImageViews[i].setImageResource(R.drawable.timeisup)
                        }
                    }
                    soundPool?.pause(tickTockId)
                    soundPool?.play(wrongSnd,1f,1f,0,0,1f)
                    timerEnd.start()
                }
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
        progressBar.max = PROGRESS_MAX
        progressBar.progress = PROGRESS_START
        soundPool?.resume(tickTockId)
    }

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
                    soundPool?.pause(tickTockId)
                    onlyOnePick = false
                    job.cancel(CancellationException("Resetting progressbar"))
                    if (i != flagQuizGame.correctAnswer) {
                        iv.setImageResource(R.drawable.wrong)
                    } else {
                        flagQuizGame.points++
                        iv.setImageResource(R.drawable.correct)
                    }
                    if (i != flagQuizGame.correctAnswer || !flagQuizGame.checkFlagsLeft()) {
                        if (i != flagQuizGame.correctAnswer) {
                            val tvCorrect = gameImageViews[flagQuizGame.correctAnswer]
                            tvCorrect.startAnimation(
                                AnimationUtils.loadAnimation(
                                    context,
                                    R.anim.blink
                                )
                            )
                            soundPool?.play(wrongSnd,1f,1f,0,0,1f)
                        } else {
                            soundPool?.play(rightSnd,1f,1f,0,0,1f)
                        }
                        timerEnd.start()
                    } else {
                        soundPool?.play(rightSnd,1f,1f,0,0,1f)
                        timerNew.start()
                    }
                }
            }
        }
    }

    interface onEndOfGame{
        fun onEndOfGame(points : Int, noOfFlags : Int)
    }
}