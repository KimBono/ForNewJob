package com.example.todolist.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.Greeting
import com.example.todolist.R
import com.example.todolist.domain.model.Todo
import com.example.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel){
    var text by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(title = {Text("오늘 할일")} )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = text,
                onValueChange = {text = it},
                placeholder = {Text("할일")},
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.addTodo(text)
                    text = ""
                    keyboardController?.hide()
                })
            )

            Divider()

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(viewModel.items.value){todoItem ->
                    Column() {
                        TodoItem(
                            todo = todoItem,
                            onClick = {todo ->
                                viewModel.toggle(todo.uid)
                            },
                            onDeleteClick = {todo ->
                                viewModel.delete(todo.uid)
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "할 일 삭제됨",
                                        actionLabel = "취소"
                                    )
                                    if(result == SnackbarResult.ActionPerformed){
                                        viewModel.restoreTodo()
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(thickness = 1.dp)
                    }

                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo : Todo,
    onClick : (todo :Todo)-> Unit = {},
    onDeleteClick : (todo : Todo) -> Unit = {},
){
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Row(){
        Icon(
            painter = painterResource(id = R.drawable.baseline_delete_24),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDeleteClick(todo) }
            )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = format.format(Date(todo.data)),
                color = if(todo.isDone) Color.Gray else Color.Black,
                style = TextStyle(
                    textDecoration
                    = if(todo.isDone) TextDecoration.LineThrough
                    else TextDecoration.None
                )
            )
            Text(text = todo.title,
                color = if(todo.isDone) Color.Gray else Color.Black,
                style = TextStyle(
                    textDecoration
                    = if(todo.isDone) TextDecoration.LineThrough
                    else TextDecoration.None)
            )
        }
        if(todo.isDone) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_done_24),
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GMainScreenPreview() {
    ToDoListTheme {
        val viewModel : MainViewModel = viewModel()
        MainScreen(viewModel)

    }
}
