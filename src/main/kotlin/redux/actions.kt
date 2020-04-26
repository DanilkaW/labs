package redux

import data.Lesson
import data.Student

class ChangePresent(val lesson: Int, val student: Int) : RAction
class addStudent (val studentName: String, val studentSurname: String) :RAction
class addLesson (val lesson: String) :RAction
class deleteStudent (val num: Int) :RAction
class deleteLesson (val num: Int) :RAction