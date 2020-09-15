package se.ctescape.flagquiz

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    lateinit var etName : EditText
    lateinit var tvHiScore : TextView
    lateinit var btnStart : Button
    val chkBoxes = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById<EditText>(R.id.etName)
        tvHiScore = findViewById<TextView>(R.id.tvHiScore)
        btnStart = findViewById<Button>(R.id.btnStart)
        chkBoxes.add(findViewById(R.id.ck1))
        chkBoxes.add(findViewById(R.id.ck2))
        chkBoxes.add(findViewById(R.id.ck3))
        chkBoxes.add(findViewById(R.id.ck4))
        chkBoxes.add(findViewById(R.id.ck5))

        btnStart.setOnClickListener {
            val playerName = etName.text.toString()
            if (etName.text.toString() != "") {
                checkInput()
            } else {
                basicAlert("Inget namn angivet!", "Utan namn kommer inte Hiscore att sparas")
            }
        }
    }

    fun checkInput(){
        val playerName = etName.text.toString()
        with(sharedPref.edit()) {
            putString("last", playerName)
            putInt(playerName, 0)
            commit()
        }
        if (checkBoxesEmpty()){
            chkBoxes[0].isChecked = true
            basicAlert("Inget område valt!", "Område \"Norden\" kommer att väljas som default.")
        } else {
            val intent = Intent(this, GameActivity::class.java)
            var cnt = 0
            intent.putExtra("noOfBools", chkBoxes.size)
            for(chkBox in chkBoxes){
                intent.putExtra("bool$cnt", chkBox.isChecked)
                cnt++
            }
            Log.d("AIK", "We are running")
            startActivity(intent)
        }
    }

    fun checkBoxesEmpty(): Boolean{
        for(chkBox in chkBoxes){
            if (chkBox.isChecked)
                return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        val playerName = sharedPref.getString("last", "")
        if (playerName != "") {
            etName.setText(playerName)
            val hiscoreText = sharedPref.getInt(playerName, 0).toString()
            tvHiScore.text = "$hiscoreText rätt"
        } else {
            etName.setText("")
            tvHiScore.text = "0 rätt"
        }
    }

    fun basicAlert(title: String, msg: String){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(title)
            setMessage(msg)
            setPositiveButton("OK") { dialogInterface, i ->
                checkInput()
            }
            setNegativeButton("Avbryt", null)
            show()
        }
    }
}