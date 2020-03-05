package data

data class Student (
    val firstname: String,
    val surname: String,
    var presence: Boolean
)

val studentList =
    arrayListOf(
        Student("Sheldon", "Cooper",true),
        Student("Almat", "Sultanov",true),
        Student("Howard", "Wolowitz",true)
    )