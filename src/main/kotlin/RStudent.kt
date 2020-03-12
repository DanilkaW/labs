import data.Student
import react.*
import react.dom.li
import react.dom.ol

interface RStudentProps : RProps {
    var student: Student
}

interface RStudentListProps : RProps {
    var students : Array<Student>
}

fun RBuilder.rstudent(student: Student) =
    child(
        functionalComponent<RStudentProps> {
            +"${it.student.firstname} ${it.student.surname}"
        }
    ){
        attrs.student = student
    }

fun RBuilder.rstudentList(students: Array<Student>) =
       child(functionalComponent<RStudentListProps> { props ->
           props.students.map {
               li {
                   rstudent(it)
               }
           }
       }){
           attrs.students = students
       }