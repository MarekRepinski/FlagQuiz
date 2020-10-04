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
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import se.ctescape.flagquiz.data.FQRepository
import se.ctescape.flagquiz.data.FQdatabase
import se.ctescape.flagquiz.data.FillDataBase
// TODO: Add Oceania - data
// TODO: Soundeffects
// TODO: Animation
// TODO: Add practice
// TODO: Clean up and comment the code
class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var tvHiScore: TextView
    private lateinit var btnStart: Button
    private val chkBoxes = mutableListOf<CheckBox>()
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("fqprefs", Context.MODE_PRIVATE)
        etName = findViewById<EditText>(R.id.etName)
        tvHiScore = findViewById<TextView>(R.id.tvHiScore)
        btnStart = findViewById<Button>(R.id.btnStart)
        chkBoxes.add(findViewById(R.id.ck1))
        chkBoxes.add(findViewById(R.id.ck2))
        chkBoxes.add(findViewById(R.id.ck3))
        chkBoxes.add(findViewById(R.id.ck4))
        chkBoxes.add(findViewById(R.id.ck5))

        val dbFill = FillDataBase(this)

        val contex = this
        wait_bar.visibility = View.VISIBLE
        CoroutineScope(IO).launch {
            var timeLimit = 0
            while (!dbFill.finished && timeLimit < 20) {
                delay(500)
                timeLimit++
            }
            withContext(Dispatchers.Main) {
                wait_bar.visibility = View.INVISIBLE
                if (timeLimit >= 20){
                    Toast.makeText(contex, "Loading database is taking too long", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnStart.setOnClickListener {
            if (dbFill.finished) {
                if (etName.text.toString() != "") {
                    checkInput()
                } else {
                    basicAlert(getString(R.string.basicHeader2), getString(R.string.basicInfo2))
                }
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

    fun checkInput() {
        val playerName = etName.text.toString()
        val newPlayer = !sharedPref.contains(playerName)
        with(sharedPref.edit()) {
            putString("qeQ0EqeLastqeQ0Eqe", playerName)
            if (newPlayer)
                putInt(playerName, 0)
            commit()
        }
        if (checkBoxesEmpty()) {
            chkBoxes[0].isChecked = true
            basicAlert(getString(R.string.basicHeader1), getString(R.string.basicInfo1))
        } else {
            val context = this
            wait_bar.visibility = View.VISIBLE
            DataManager.flagLista.clear()
            CoroutineScope(IO).launch {
                if (ck5.isChecked){
                    loadDataManager(context, "america")
                }
                if (ck4.isChecked){
                    loadDataManager(context, "africa")
                }
                if (ck3.isChecked){
                    loadDataManager(context, "asia")
                }
                if (ck2.isChecked) {
                    loadDataManager(context, "europe")
                    ck1.isChecked = true
                }
                if (ck1.isChecked){
                    loadDataManager(context,"scandinavia")
                }
                withContext(Dispatchers.Main){//Funkar bara från coroutines
                    wait_bar.visibility = View.INVISIBLE
                    val intent = Intent(context, GameActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    suspend fun loadDataManager(context: Context, continent: String){
        val repository = FQRepository(FQdatabase.getDatabase(context).FQdao())
        CoroutineScope(IO).launch {
            val list = repository.getContinent(continent)
            list.forEach {
                DataManager.flagLista.add(Flag(it.enCountry, it.svCountry, it.plCountry, it.flagId))
            }
        }
    }

    fun checkBoxesEmpty(): Boolean {
        for (chkBox in chkBoxes) {
            if (chkBox.isChecked)
                return false
        }
        return true
    }

    @SuppressLint("StringFormatInvalid")
    override fun onStart() {
        super.onStart()
        val playerName = sharedPref.getString("qeQ0EqeLastqeQ0Eqe", "") ?: ""
        if (playerName != "") {
            etName.setText(playerName)
            val hiscoreText = sharedPref.getInt(playerName, 0).toString()
            tvHiScore.text = getString(R.string.hiScoreDefText, hiscoreText)
        } else {
            etName.setText("")
            tvHiScore.text = getString(R.string.hiScoreDefText, "0")
        }
    }

    fun basicAlert(title: String, msg: String) {
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