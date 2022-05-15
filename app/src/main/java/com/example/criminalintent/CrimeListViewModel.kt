package com.example.criminalintent

import androidx.lifecycle.ViewModel
import com.example.criminalintent.models.Crime

class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crime.requiresPolice = when((0..1).shuffled().first()) {
                0 -> false
                else -> true
            }
            crimes += crime
        }
    }

}