package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etName : EditText
    private lateinit var tvHiScore : TextView
    private lateinit var btnStart : Button
    private val chkBoxes = mutableListOf<CheckBox>()
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref= getSharedPreferences("fqprefs", Context.MODE_PRIVATE)
        etName = findViewById<EditText>(R.id.etName)
        tvHiScore = findViewById<TextView>(R.id.tvHiScore)
        btnStart = findViewById<Button>(R.id.btnStart)
        chkBoxes.add(findViewById(R.id.ck1))
        chkBoxes.add(findViewById(R.id.ck2))
        chkBoxes.add(findViewById(R.id.ck3))
        chkBoxes.add(findViewById(R.id.ck4))
        chkBoxes.add(findViewById(R.id.ck5))

        btnStart.setOnClickListener {
            if (etName.text.toString() != "") {
                checkInput()
            } else {
                basicAlert(getString(R.string.basicHeader2), getString(R.string.basicInfo2))
            }
        }

        etName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val playerExists = sharedPref.contains(etName.text.toString())
                if (playerExists) {
                    val hiscoreText = sharedPref.getInt(etName.text.toString(), 0).toString()
                    tvHiScore.text = getString(R.string.hiScoreDefText, hiscoreText)
                } else {
                    tvHiScore.text = getString(R.string.hiScoreDefText, "0")
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                /* no-op */
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* no-op */
            }
        })
    }

    fun checkInput(){
        val playerName = etName.text.toString()
        val newPlayer = !sharedPref.contains(playerName)
        with(sharedPref.edit()) {
            putString("qeQ0EqeLastqeQ0Eqe", playerName)
            if (newPlayer)
                putInt(playerName, 0)
            commit()
        }
        if (checkBoxesEmpty()){
            chkBoxes[0].isChecked = true
            basicAlert(getString(R.string.basicHeader1), getString(R.string.basicInfo1))
        } else {
            val intent = Intent(this, GameActivity::class.java)
            var cnt = 0
            intent.putExtra("noOfBools", chkBoxes.size)
            for(chkBox in chkBoxes){
                intent.putExtra("bool$cnt", chkBox.isChecked)
                cnt++
            }
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

    @SuppressLint("StringFormatInvalid")
    override fun onStart() {
        super.onStart()
        val playerName = sharedPref.getString("qeQ0EqeLastqeQ0Eqe", "")?:""
        if (playerName != "") {
            etName.setText(playerName)
            val hiscoreText = sharedPref.getInt(playerName, 0).toString()
            tvHiScore.text = getString(R.string.hiScoreDefText, hiscoreText)
        } else {
            etName.setText("")
            tvHiScore.text = getString(R.string.hiScoreDefText, "0")
        }
    }

    fun basicAlert(title: String, msg: String){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(title)
            setMessage(msg)
            setPositiveButton(getString(R.string.ok)) { dialogInterface, i ->
                checkInput()
            }
            setNegativeButton(getString(R.string.cancel), null)
            show()
        }
    }
}