package se.ctescape.flagquiz

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), FragmentMainGame.onEndOfGame {
    val gameImageViews = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val noOfBools = intent.getIntExtra("noOfBools", 0)
        if (noOfBools > 0) {
            val areas = BooleanArray(intent.getIntExtra("noOfBools", 0))
            for (i in 0..(noOfBools - 1)) {
                areas[i] = intent.getBooleanExtra("bool$i", false)
            }
            val f = FragmentMainGame.newInstance(areas)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.frmMainGame, f, "gameFragment")
            transaction.commit()
        }
    }

    override fun onEndOfGame(points: Int) {
        Toast.makeText(this,"Your score was $points", Toast.LENGTH_LONG).show()
    }

}