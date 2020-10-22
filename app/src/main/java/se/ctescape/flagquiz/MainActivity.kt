package se.ctescape.flagquiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.ctescape.flagquiz.data.FQRepository
import se.ctescape.flagquiz.data.FQdatabase
import se.ctescape.flagquiz.data.FillDataBase

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText           //Player name
    private lateinit var tvHiScore: TextView        //Player hiscore
    private lateinit var btnStart: Button           //Start game button
    //Continent Choices - checkbox
    private val chkBoxes = mutableListOf<CheckBox>()
    //Store hiscore and last player in SharedPref
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init all lateinit variables
        setContentView(R.layout.activity_main)
        etName = findViewById<EditText>(R.id.etName)
        tvHiScore = findViewById<TextView>(R.id.tvHiScore)
        btnStart = findViewById<Button>(R.id.btnStart)
        chkBoxes.add(findViewById(R.id.ck1))
        chkBoxes.add(findViewById(R.id.ck2))
        chkBoxes.add(findViewById(R.id.ck3))
        chkBoxes.add(findViewById(R.id.ck4))
        chkBoxes.add(findViewById(R.id.ck5))
        chkBoxes.add(findViewById(R.id.ck6))
        sharedPref = getSharedPreferences("fqprefs", Context.MODE_PRIVATE)

        //Init ROOM database
        val dbFill = FillDataBase(this)

        //Wait for the database to load and show a wait bar
        val contex = this
        wait_bar.visibility = View.VISIBLE
        CoroutineScope(IO).launch {
            var timeLimit = 0
            while (!dbFill.finished && timeLimit < 20) {
                delay(500)
                timeLimit++
            }
            withContext(Main) {
                wait_bar.visibility = View.INVISIBLE
                if (timeLimit >= 20){
                    Toast.makeText(contex, "Loading database is taking too long", Toast.LENGTH_LONG).show()
                }
            }
        }

        //Wait for someone to push the startbutton
        btnStart.setOnClickListener {
            if (dbFill.finished) {
                if (etName.text.toString() != "") {
                    checkInput()
                } else {
                    basicAlert(getString(R.string.basicHeader2), getString(R.string.basicInfo2))
                }
            }
        }

        //Wait for someone to push the practicebutton
        btnPratice.setOnClickListener {
            if (dbFill.finished) {
                checkInput(false)
            }
        }

        //Find hiscore of entered playername
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

            //These two overriders must be here to make it work
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                /* no-op */
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* no-op */
            }
        })
    }

    //On start load last player and hiscore from Sharedpref
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

    //Check input and start the game or practice
    fun checkInput(playGame: Boolean = true) {
        val playerName = etName.text.toString()
        val newPlayer = !sharedPref.contains(playerName) //If player not in SharedPref
        //Set last player in SharedPref and hiscore = 0 if new player
        with(sharedPref.edit()) {
            putString("qeQ0EqeLastqeQ0Eqe", playerName)
            if (newPlayer)
                putInt(playerName, 0)
            commit()
        }
        if (checkBoxesEmpty()) {
            chkBoxes[0].isChecked = true
            basicAlert(getString(R.string.basicHeader1), getString(R.string.basicInfo1)) //OK on this will run checkInput again with Scandinavia chosen
        } else {
            val context = this //Save this as contex to be used in Coroutines
            wait_bar.visibility = View.VISIBLE //Start wait bar
            if (ck2.isChecked) {
                ck1.isChecked = true //If Europe is choden then chose also Scandinavia
            }
            DataManager.flagLista.clear() //Clear the DataManager
            //Load datamanager in a Coroutine
            CoroutineScope(IO).launch {
                if (ck6.isChecked){
                    loadDataManager(context, "oceania")
                }
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
                }
                if (ck1.isChecked){
                    loadDataManager(context,"scandinavia")
                }
                withContext(Main){//Start game or practice in main thread
                    wait_bar.visibility = View.INVISIBLE
                    // startActivity is a Kotlin command that starts an intent
                    if (playGame) {
                        startActivity(Intent(context, GameActivity::class.java))
                    } else {
                        startActivity(Intent(context, PracticeActivity::class.java))
                    }
                }
            }
        }
    }

    //Load datamanager from databse in Coroutine
    suspend fun loadDataManager(context: Context, continent: String){
        val repository = FQRepository(FQdatabase.getDatabase(context).FQdao())
        CoroutineScope(IO).launch {
            val list = repository.getContinent(continent)
            list.forEach {
                DataManager.flagLista.add(Flag(it.enCountry, it.svCountry, it.plCountry, it.flagId))
            }
        }
    }

    //Check if all checkboxes are empty
    fun checkBoxesEmpty(): Boolean {
        for (chkBox in chkBoxes) {
            if (chkBox.isChecked)
                return false
        }
        return true
    }

    //Show a dialogbox on screen
    fun basicAlert(title: String, msg: String) {
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle(title)
            setMessage(msg)
            //If "OK" is pressed the game will start
            setPositiveButton(getString(R.string.ok)) { dialogInterface, i ->
                checkInput()
            }
            setNegativeButton(getString(R.string.cancel), null)
            show()
        }
    }
}