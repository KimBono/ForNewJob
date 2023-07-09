package com.example.myapplication

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask> = rememberSaveable { getWellnessTasks() },
    onCloseTask: (WellnessTask) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = {task -> task.id}) { task ->
            WellnessTaskItem(taskName = task.label,
                            onClose = {onCloseTask(task)})
        }
    }
}