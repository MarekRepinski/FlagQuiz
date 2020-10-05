package se.ctescape.flagquiz

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class GameActivity : AppCompatActivity(), FragmentMainGame.onEndOfGame, FragmentEndGame.onRestartGame {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var c : ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimaryDark))

        val f = FragmentMainGame()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frmMainGame, f, "gameFragment")
        transaction.commit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onEndOfGame(points: Int, noOfFlags : Int) {
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimary))
        val f = FragmentEndGame.newInstance(points, noOfFlags)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frmMainGame, f, "endFragment")
        transaction.commit()
    }

    override fun onRestartGame() {
        this.onBackPressed()
    }
}