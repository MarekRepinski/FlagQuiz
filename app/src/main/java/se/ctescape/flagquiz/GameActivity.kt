package se.ctescape.flagquiz

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class GameActivity : AppCompatActivity(), FragmentMainGame.onEndOfGame, FragmentEndGame.onRestartGame {
    val gameImageViews = mutableListOf<ImageView>()
    lateinit var c : ConstraintLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimaryDark))

        val noOfBools = intent.getIntExtra("noOfBools", 0)
        if (noOfBools > 0) {
            val areas = BooleanArray(intent.getIntExtra("noOfBools", 0))
            for (i in 0..(noOfBools - 1)) {
                areas[i] = intent.getBooleanExtra("bool$i", false)
            }
            val f = FragmentMainGame.newInstance(areas)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frmMainGame, f, "gameFragment")
            transaction.commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onEndOfGame(points: Int) {
        c = findViewById(R.id.gameConstrain)
        c.setBackgroundColor(getColor(R.color.colorPrimary))
        val f = FragmentEndGame()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frmMainGame, f, "endFragment")
        transaction.commit()
        Toast.makeText(this,"Your score was $points", Toast.LENGTH_LONG).show()
    }

    override fun onRestartGame() {
        this.onBackPressed()
    }
}