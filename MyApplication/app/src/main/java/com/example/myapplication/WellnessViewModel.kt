package com.example.myapplication

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }


class WellnessViewModel : ViewModel(){
    private val _tasks = getWellnessTasks().toMutableStateList()
    val task: List<WellnessTask>
        get() = _tasks


}