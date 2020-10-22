package se.ctescape.flagquiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
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
    val gameImageViews = mutableListOf<ImageView>() //List of images for flags
    val starImageViews = mutableListOf<ImageView>() //List of stars - animation
    lateinit var flagQuizGame: FlagQuiz     //Holder of the flagQuizGame class
    var onlyOnePick = false                 //Make sure only one answer is picked
    lateinit var rubrik : TextView          //Holder for the text header
    lateinit var question : TextView        //Holder for the question header
    private lateinit var listener : onEndOfGame     //Used to send back data to parent view
    private val PROGRESS_MAX = 100                  //Start value (%) of the bar
    private val PROGRESS_START = 0                  //End value (%) of the bar
    private val JOB_TIME = 10000                    //How long time the bar is running (ms)
    private lateinit var progressBar: ProgressBar   //Holder for the bar
    private lateinit var job: CompletableJob        //Holder for the job (used with bar)
    private var soundPool: SoundPool? = null        //Holder for the soounds
    private var rightSnd = 0                        //Holder for the correct sound
    private var wrongSnd = 0                        //Holder for the the wrong sound
    private var tickTockSnd = 0                     //Holder for the timer sound
    private var tickTockId = 0                      //ID for the timer sound

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Used to send parameters back to parent view
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
            starImageViews.add(v.findViewById<ImageView>(resources.getIdentifier("star$i", "id", activity!!.packageName)))
            starImageViews[i].visibility = ImageView.INVISIBLE //Make all stars invisilble
            //Set a listener on each flag
            gameImageViews[i].setOnTouchListener { view, e ->
                getAnswer(e, i)
                true
            }
        }

        //Load all sounds
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
        tickTockSnd = soundPool!!.load(context, R.raw.clock, 3)
        //Start timer sound
        soundPool?.setOnLoadCompleteListener { soundPool, i, i2 ->
            tickTockId = soundPool!!.play(tickTockSnd,1f,1f,0,-1,1f)
        }
        soundPool!!.pause(tickTockId) //Pause timer sound

        //Start the game and check if there is any flags left
        flagQuizGame = FlagQuiz()
        if (!flagQuizGame.checkFlagsLeft())
            endGame()
        else
            printNewFlags()
        return v
    }

    //Release sounds from memory
    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }

    @SuppressLint("StringFormatInvalid")
    fun printNewFlags() {
        initJob()
        progressBar.startJobOrCancel(job)   //Start progressbar

        val idArray = flagQuizGame.printFlags()     //Get 4 random flags
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

    //Init the progressbar as a job
    private fun initJob() {
        job = Job()
        progressBar.max = PROGRESS_MAX
        progressBar.progress = PROGRESS_START
        soundPool!!.resume(tickTockId)
    }

    //If the Progressbar is stopped, start it
    fun ProgressBar.startJobOrCancel(job: Job){
        if (this.progress > 0){
            /* no-op */
        } else {
            //Start Progressbar countdown in Coroutine
            //IO + job skapar ett nytt unikt context
            CoroutineScope(Dispatchers.IO + job).launch{
                for(i in PROGRESS_START..PROGRESS_MAX){
                    delay((JOB_TIME/PROGRESS_MAX).toLong())
                    this@startJobOrCancel.progress = i          //Set progress in bar
                }
                //When time is up launch in Main thread
                GlobalScope.launch(Main){
                    onlyOnePick = false
                    for(i in 0..3){
                        if (i == flagQuizGame.correctAnswer){
                            gameImageViews[i].startAnimation(AnimationUtils.loadAnimation(context, R.anim.blink))
                        } else {
                            gameImageViews[i].setImageResource(R.drawable.timeisup)
                        }
                    }
                    soundPool!!.pause(tickTockId)
                    soundPool!!.play(wrongSnd,1f,1f,0,0,1f)
                    timerEnd.start()
                }
            }
        }
    }

    //Send parameters back to parent view
    fun endGame() {
        listener.onEndOfGame(flagQuizGame.points, flagQuizGame.noOfFlags())
    }

    //Check user interaction - right or wrong answer
    fun getAnswer(e: MotionEvent, i: Int) {
        if (onlyOnePick) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    /* no-op */
                }
                MotionEvent.ACTION_UP -> {
                    soundPool!!.pause(tickTockId)           //Pause ticking sound
                    onlyOnePick = false                     //Prevent more selections
                    //Cancel progressbar (and its job)
                    job.cancel(CancellationException("Resetting progressbar"))
                    if (i != flagQuizGame.correctAnswer) {
                        wrongAnswer(i)
                    } else {
                        rightAnswer(i)
                    }
                    if (i != flagQuizGame.correctAnswer || !flagQuizGame.checkFlagsLeft()) {
                        timerEnd.start()
                    } else {
                        timerNew.start()
                    }
                }
            }
        }
    }

    //Wrong answer - blink the flag of the right answer
    private fun wrongAnswer(i: Int){
        gameImageViews[i].setImageResource(R.drawable.wrong)
        soundPool!!.play(wrongSnd,1f,1f,0,0,1f)
        val tvCorrect = gameImageViews[flagQuizGame.correctAnswer]
        tvCorrect.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.blink
            )
        )
    }

    //Animate a star on right answer
    private fun rightAnswer(i: Int){
        flagQuizGame.points++
        soundPool!!.play(rightSnd,1f,1f,0,0,1f)
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f,8f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f,8f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(starImageViews[i], scaleX, scaleY)
        animator.disableViewDuringAnimation(starImageViews[i], gameImageViews[i])
        animator.repeatCount = 0
        animator.repeatMode = ObjectAnimator.RESTART
        animator.duration = 300
        animator.start()
    }

    //What happen on start and end of animation
    private fun ObjectAnimator.disableViewDuringAnimation(v1: View, v2: ImageView) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                v1.visibility = ImageView.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                v1.visibility = ImageView.INVISIBLE
                v2.setImageResource(R.drawable.correct) //Set "correct" image
            }
        })
    }

    //Used to send parameters back to parent view
    interface onEndOfGame{
        fun onEndOfGame(points : Int, noOfFlags : Int)
    }

    //Timer to show the correct answer (1 sec) before next flag is displayed
    private val timerNew = object: CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            printNewFlags()
        }
    }

    //Timer to show the correct answer (3 sec) before game is ended
    private val timerEnd = object: CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            /* no-op */
        }

        override fun onFinish() {
            endGame()
        }
    }
}