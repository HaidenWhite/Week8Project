package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun App() {
    val stateList = remember { mutableStateListOf<Student>() }
    Column {
        AddStudent(onStudentAdded = {
            stateList.add(it)
        })

        StudentList(stateList.toList())
    }
}

@Composable
fun StudentList(studentList: List<Student>) {
    Column{
        studentList.forEach {
            Text("$it")
        }
    }
}

@Composable
fun AddStudent(onStudentAdded: (Student) -> Unit) {
    val studentId = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val course = remember { mutableStateOf("") }
    val mark = remember { mutableStateOf("") }
    Column {
        TextField(value = studentId.value, onValueChange = { studentId.value = it }, label = { Text("Student ID") })
        TextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name") })
        TextField(value = course.value, onValueChange = { course.value = it }, label = { Text("Course") })
        TextField(value = mark.value, onValueChange = { mark.value = it }, label = { Text("Mark") })
        Button(onClick = {
            val currentStudent = Undergraduate(id = studentId.value, name = name.value, course = course.value)
            currentStudent.mark = mark.value.toInt()
            onStudentAdded(currentStudent)
        }) {
            Text("Add Student")
        }
        }
}