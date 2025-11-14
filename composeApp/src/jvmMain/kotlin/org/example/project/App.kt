package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun App() {
    val university = University()

    UniversityComposable(university)
}

@Composable
fun UniversityComposable(university: University) {
    Column {
        val stateList = remember { mutableStateListOf<Student>() }
        val studentState = remember { mutableStateOf("") }
        AddStudent(studentState.value, onStudentAdded = {
            university.addStudent(it)
            stateList.clear()
            stateList.addAll(university.studentList)
        })

        DropdownList(listOf("Undergraduate", "Masters"), onItemSelected = {
            studentState.value = it
        })

        SearchByCourse(onCourseSearched = {
            stateList.clear()
            stateList.addAll(university.findStudentsByCourse(it))
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
fun AddStudent(studentState: String, onStudentAdded: (Student) -> Unit) {
    val studentId = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val course = remember { mutableStateOf("") }
    val mark = remember { mutableStateOf("") }
    Column {
        TextField(singleLine=true, value = studentId.value, onValueChange = { studentId.value = it }, label = { Text("Student ID") })
        TextField(singleLine=true,value = name.value, onValueChange = { name.value = it }, label = { Text("Name") })
        TextField(singleLine=true,value = course.value, onValueChange = { course.value = it }, label = { Text("Course") })
        TextField(singleLine=true, value = mark.value, onValueChange = { mark.value = it }, label = { Text("Mark") })
        Button(onClick = {
            if (studentState == "Masters"){
                val currentMasters = Masters(id = studentId.value, name = name.value, course = course.value)
                currentMasters.mark = mark.value.toInt()
                onStudentAdded(currentMasters)
            }
            else {
                val currentUndergraduate = Undergraduate(id = studentId.value, name = name.value, course = course.value)
                currentUndergraduate.mark = mark.value.toInt()
                onStudentAdded(currentUndergraduate)
            }
        }) {
            Text("Add Student")
        }
    }
}

@Composable
fun SearchByCourse(onCourseSearched: (String) -> Unit) {
    val course = remember { mutableStateOf("") }
    Column {
        Text("Enter course")
        TextField(value = course.value, onValueChange = { course.value = it })
        Button(onClick = {
            onCourseSearched(course.value)
        }) {
            Text("Search")
        }
    }
}

@Composable
fun DropdownList(items: List<String>, onItemSelected: (String)->Unit = { }) {
    val dropDownVisible = remember { mutableStateOf(false) }
    Column {
        Button( onClick =  { dropDownVisible.value = !dropDownVisible.value }) {
            Text("Please select...")
        }
        DropdownMenu(expanded = dropDownVisible.value, onDismissRequest = {
            dropDownVisible.value = false
        }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    onItemSelected(item)
                })
            }
        }
    }
}