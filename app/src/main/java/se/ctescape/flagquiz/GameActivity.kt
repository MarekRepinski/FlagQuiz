package se.ctescape.flagquiz

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

//Interface FragmentMainGame.onEndOfGame id used to get returndata back from fragment.
//This is done with an override of onEndOfGame... to get no of points and no of flags
//Interface FragmentEndGame.onRestartGame force the built in back function in endGame fragment
class GameActivity : AppCompatActivity(), FragmentMainGame.onEndOfGame, FragmentEndGame.onRestartGame {
    lateinit var c : ConstraintLayout //Only used to change background color

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimaryDark))

        //Init fragment for the game
        val f = FragmentMainGame()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frmMainGame, f, "gameFragment") //Replace fragment in frame
        transaction.commit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onEndOfGame(points: Int, noOfFlags : Int) {
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimary))

        //Init fragment for the end game screen
        val f = FragmentEndGame.newInstance(points, noOfFlags)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frmMainGame, f, "endFragment")
        transaction.commit()
    }

    override fun onRestartGame() {
        this.onBackPressed()
    }
}