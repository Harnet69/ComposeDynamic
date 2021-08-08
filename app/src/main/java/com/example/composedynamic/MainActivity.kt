package com.example.composedynamic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val usersList = arrayListOf("Adam", "Michel", "George", "John")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val greetingListState = remember { mutableStateListOf<String>("Adam", "Michel") }
    val newNameStateContent = remember { mutableStateOf("") }

    Surface(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxSize()
    ) {
        GreetingsBlock(
            greetingListState,
            // add new name button
            { greetingListState.add(newNameStateContent.value) },
            //textField value
            newNameStateContent.value,
            // text field update which triggering a UI's recomposition
            { newName -> newNameStateContent.value = newName }
        )
    }
}

@Composable
fun GreetingsBlock(
    greetingList: List<String>,
    addButtonClick: () -> Unit,
    nameTextFieldValue: String,
    nameTextFieldUpdate: (newName: String) -> Unit
) {

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(6.dp),
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        GreetingsList(greetingList)
        //Input field
        TextField(value = nameTextFieldValue, onValueChange = nameTextFieldUpdate)
        //Add button
        Button(onClick = addButtonClick) {
            Text(text = "Add a new user")

        }
    }
}

@Composable
fun GreetingsList(greetingList: List<String>) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(6.dp),
        Arrangement.SpaceEvenly,
        Alignment.CenterHorizontally
    ) {
        greetingList.forEach {
            Greeting(name = it)
        }
    }
}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    Text(
        color = Color.LightGray,
        text = "Hello $name!", modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .padding(3.dp)
            .background(Color.Magenta)
            .clickable {
                Toast
                    .makeText(context, "You said hello to $name", Toast.LENGTH_LONG)
                    .show()
            },
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h3
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}