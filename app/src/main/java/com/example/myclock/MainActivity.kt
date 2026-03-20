package com.example.myclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp   // ✅ FIXED
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()   // ✅ USED
        }
    }
}

@Composable
fun MainScreen() {
    var selected by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = selected == 0, onClick = { selected = 0 }, label = { Text("Clock") }, icon = {})
                NavigationBarItem(selected = selected == 1, onClick = { selected = 1 }, label = { Text("Alarm") }, icon = {})
                NavigationBarItem(selected = selected == 2, onClick = { selected = 2 }, label = { Text("Timer") }, icon = {})
                NavigationBarItem(selected = selected == 3, onClick = { selected = 3 }, label = { Text("Stopwatch") }, icon = {})
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selected) {
                0 -> ClockScreen()
                1 -> AlarmScreen()
                2 -> TimerScreen()
                3 -> StopwatchScreen()
            }
        }
    }
}

@Composable
fun ClockScreen() {
    var time by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = time, fontSize = 40.sp)
    }
}

@Composable
fun TimerScreen() {
    var timeLeft by remember { mutableStateOf(60) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        if (running) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            running = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Time: $timeLeft", fontSize = 30.sp)

        Button(onClick = { running = true }) {
            Text("Start Timer")
        }
    }
}

@Composable
fun StopwatchScreen() {
    var time by remember { mutableStateOf(0L) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        while (running) {
            delay(100)
            time += 100
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Time: ${time / 1000}", fontSize = 30.sp)

        Row {
            Button(onClick = { running = true }) { Text("Start") }
            Spacer(modifier = Modifier.width(10.dp))   // ✅ FIXED
            Button(onClick = { running = false }) { Text("Stop") }
            Spacer(modifier = Modifier.width(10.dp))   // ✅ FIXED
            Button(onClick = { time = 0 }) { Text("Reset") }
        }
    }
}

@Composable
fun AlarmScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Alarm coming soon 😏", fontSize = 24.sp)
    }
}