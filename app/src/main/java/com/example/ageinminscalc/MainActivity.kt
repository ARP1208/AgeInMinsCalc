package com.example.ageinminscalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    /*
    We are creating this TextView variable as a knowledgeable so that I can set it to null because I will not initialize it at this point.
    So we are not assigning a value to it at this point.
    We leave it empty until the point where I'm going to actually assign it and that is usually in the onCreate() method.
    // It's a good practice to set it up in the on create method.
     */

    private var ageinMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDatePicker: Button = findViewById(R.id.buttonDatePicker)

        ageinMinutes = findViewById(R.id.ageinMinutes)
        tvSelectedDate = findViewById(R.id.selectedDate)

        buttonDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,
                    "Year is $selectedYear, Month is ${selectedMonth+1}, Day is $selectedDayOfMonth", Toast.LENGTH_SHORT).show()

                val dateSelected = "$selectedDayOfMonth/ ${selectedMonth+1}/ $selectedYear"

                tvSelectedDate?.text = dateSelected
                // tvSelectedDate?.setText(dateSelected) //alternate way to write the pevious line.
                // ".setText() is used in order to set the text.
                // Except for "." we use "?." because it is a nullable.

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.JAPANESE)
                // SimpleDateFormat(): It allows us to do is to define a pattern that we want to use for our date.

                val theDate = sdf.parse(dateSelected)
                theDate?.let {

                    val selectedDateinMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate.let {
                        val currentDateinMinutes = currentDate.time / 60000

                        val differenceinMinutes = currentDateinMinutes - selectedDateinMinutes

                        ageinMinutes?.text = differenceinMinutes.toString()
                    }
                }


            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}