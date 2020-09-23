package se.ctescape.flagquiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.lang.ClassCastException

private const val ARG_PARAM1 = "points"
private const val ARG_PARAM2 = "noOfFlags"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentEndGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentEndGame : Fragment() {
    private var points: Int = 0
    private var noOfFlags: Int = 0
    private lateinit var listener: onRestartGame
    private lateinit var backButton: Button
    private val namesHi: Array<String> = arrayOf<String>("No Name", "No Name", "No Name")
    private val pointsHi = arrayOf<Int>(0, 0, 0)
    private var currentPlayer = ""
    private lateinit var sharedPref: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onRestartGame)
            listener = context
        else
            throw ClassCastException(context.toString() + " must be onRestartGame")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            points = it.getInt(ARG_PARAM1)
            noOfFlags = it.getInt(ARG_PARAM2)
        }
        sharedPref= activity!!.getSharedPreferences("fqprefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_end_game, container, false)
        backButton = v.findViewById(R.id.btnPlayAgain)
        backButton.setOnClickListener {
            listener.onRestartGame()
        }
        fillPage(v)
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            FragmentEndGame().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    fun fillPage(v: View) {
        var procentage = 0.0

        getTop3()

        var tv = v.findViewById<TextView>(R.id.tvResultTitle)
        if (points > pointsHi[0]) {
            tv.text = activity!!.getString(R.string.yourResulHiscore)
            with(sharedPref.edit()) {
                putInt(currentPlayer, points)
                commit()
            }
            Log.d(
                "AIK!!",
                "currentPlayer: $currentPlayer - points: $points - sharedPref.getInt(currentPlayer, 0): ${
                    sharedPref.getInt(
                        currentPlayer,
                        0
                    )
                }"
            )
        } else if (points > sharedPref!!.getInt(currentPlayer, 0)) {
            tv.text = activity!!.getString(R.string.yourResultPerHiscore)
            with(sharedPref.edit()) {
                putInt(currentPlayer, points)
                commit()
            }
        } else {
            tv.text = activity!!.getString(R.string.yourResultNoHiscore)
        }

        getTop3()

        tv = v.findViewById<TextView>(R.id.tvResult)
        tv.text =
            activity!!.getString(R.string.gotNoOfFlags, points.toString(), noOfFlags.toString())

        tv = v.findViewById<TextView>(R.id.tvVerdict)
        procentage = points.toDouble() / noOfFlags.toDouble()
        if (procentage > 0.8)
            tv.text = activity!!.getString(R.string.verdict1)
        else if (procentage > 0.4)
            tv.text = activity!!.getString(R.string.verdict2)
        else
            tv.text = activity!!.getString(R.string.verdict3)

        tv = v.findViewById<TextView>(R.id.tvFirstPlaceName)
        tv.text = namesHi[0]
        tv = v.findViewById<TextView>(R.id.tvFirstPlacePoints)
        tv.text = pointsHi[0].toString()
        tv = v.findViewById<TextView>(R.id.tvSecondPlaceName)
        tv.text = namesHi[1]
        tv = v.findViewById<TextView>(R.id.tvSecondPlacePoints)
        tv.text = pointsHi[1].toString()
        tv = v.findViewById<TextView>(R.id.tvThirdPlaceName)
        tv.text = namesHi[2]
        tv = v.findViewById<TextView>(R.id.tvThirdPlacePoints)
        tv.text = pointsHi[2].toString()

        tv = v.findViewById<TextView>(R.id.tvPersonalBest)
        tv.text = activity!!.getString(
            R.string.personalHiText,
            sharedPref.getInt(currentPlayer, 0).toString()
        )
    }

    fun getTop3() {
        //Restet arrays
        for (i in 0..2) {
            namesHi[i] = "No Name"
            pointsHi[i] = 0
        }

        //Get top 3 players
        sharedPref?.all?.forEach {
            //access key using it.key & value using it.value
            Log.d("AIK!! Pref values", "${it.key} : ${it.value}")
            if (it.key == "qeQ0EqeLastqeQ0Eqe") {
                currentPlayer = it.value.toString()
            } else if (it.value as Int > pointsHi[0]) {
                namesHi[2] = namesHi[1]
                pointsHi[2] = pointsHi[1]
                namesHi[1] = namesHi[0]
                pointsHi[1] = pointsHi[0]
                namesHi[0] = it.key
                pointsHi[0] = it.value as Int
            } else if (it.value as Int > pointsHi[1]) {
                namesHi[2] = namesHi[1]
                pointsHi[2] = pointsHi[1]
                namesHi[1] = it.key
                pointsHi[1] = it.value as Int
            } else if (it.value as Int > pointsHi[2]) {
                namesHi[2] = it.key
                pointsHi[2] = it.value as Int
            }
        }

        for (i in 0..2) {
            Log.d("AIK!!", namesHi[i])
            Log.d("AIK!!", pointsHi[i].toString())
        }
    }

    interface onRestartGame {
        fun onRestartGame()
    }
}