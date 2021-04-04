package com.tangux.doyourhorn

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.squareup.picasso.Picasso
import com.tangux.doyourhorn.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDataBase
    private lateinit var hornDao: HornDao
    private lateinit var horns: List<Horn>

    private lateinit var currentDate: String
    private lateinit var startTime: String

    var START_MILLI_SECONDS = 1500000L

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    var colors = arrayOf("Bleu", "Rose", "Violet", "Blanc")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        hornDao = db.hornDao()
        horns = hornDao.getAll()

        var dateFormat = SimpleDateFormat("dd-MM-yyyy")
        currentDate = dateFormat.format(Date())

        updateTextUI(START_MILLI_SECONDS)

        binding.buttonStart.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                val time  = binding.timeEditText.text.toString()
                if (time != ""){
                    time_in_milli_seconds = time.toLong() *60000L
                }
                else if (time_in_milli_seconds == 0L) {
                    time_in_milli_seconds = START_MILLI_SECONDS
                }
                startTimer(time_in_milli_seconds)
            }
        }

        binding.buttonReset.setOnClickListener {
            resetTimer()
        }


    }

    private fun pauseTimer() {

        binding.buttonStart.text = "Start"
        countdown_timer.cancel()
        isRunning = false
        binding.buttonReset.visibility = View.VISIBLE
    }

    private fun startTimer(time_in_seconds: Long) {
        var timeFormat = SimpleDateFormat("hh:mm:ss")
        startTime = timeFormat.format(Date())
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                resetTimer()
            }

            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                updateTextUI(time_in_milli_seconds)
            }
        }
        countdown_timer.start()

        binding.timeEditText.setText("")
        isRunning = true
        binding.buttonStart.text = "Pause"
        binding.buttonReset.visibility = View.INVISIBLE

    }

    private fun resetTimer() {
        var timeFormat = SimpleDateFormat("hh:mm:ss")
        var endTime: String = timeFormat.format(Date())
        var color: String = binding.spinner.selectedItem.toString()
        var img: String = binding.imageView.drawable.toString()
        var state: String
        if (time_in_milli_seconds > 0){
            state = "UNFINISHED"
        }
        else state = "FINISHED"

        var horn: Horn = Horn(0, currentDate, startTime, endTime, color, img, state)
        hornDao.insertAll(horn)
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTextUI(time_in_milli_seconds)
        binding.buttonReset.visibility = View.INVISIBLE
        binding.buttonStart.text = "Start"
    }

    private fun updateTextUI(timer: Long) {
        val minute = (timer / 1000) / 60
        val seconds = (timer / 1000) % 60

        binding.timerTextView.text = "$minute:$seconds"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(colors[position]){
            "Bleu" ->
                Picasso.get().load(R.drawable.blue_little).into(binding.imageView)
            "Rose" ->
                Picasso.get().load(R.drawable.pink_little).into(binding.imageView)
            "Violet" ->
                Picasso.get().load(R.drawable.purple_little).into(binding.imageView)
            "Blanc" ->
                Picasso.get().load(R.drawable.white_little).into(binding.imageView)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}