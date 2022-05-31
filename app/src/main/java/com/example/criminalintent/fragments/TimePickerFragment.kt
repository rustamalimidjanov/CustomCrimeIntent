package com.example.criminalintent.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*


private const val RESULT_TIME_KEY = "time_key"
private const val ARG_TIME = "time"
private const val ARG_REQUEST_CODE_TIME = "requestCodeTime"

class TimePickerFragment : DialogFragment() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        val initialHour = calendar.get(Calendar.HOUR)
        val initialMinute = calendar.get(Calendar.MINUTE)

        val timeListener = TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
            val resultTime =
                GregorianCalendar(initialYear, initialMonth, initialDay, hours, minutes).time

            val result = Bundle().apply {
                putSerializable(RESULT_TIME_KEY, resultTime)
            }

            val resultRequestCode = requireArguments().getString(ARG_REQUEST_CODE_TIME, "")
            parentFragmentManager.setFragmentResult(resultRequestCode, result)
        }

        return TimePickerDialog(
            requireContext(),
            timeListener,
            initialHour,
            initialMinute,
            true)
    }

    companion object {
        fun newInstance(date: Date, requestCode: String): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
                putString(ARG_REQUEST_CODE_TIME, requestCode)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }

        fun getSelectedTime(result: Bundle) = result.getSerializable(RESULT_TIME_KEY) as Date
    }
}