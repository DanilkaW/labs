import data.Student
import data.studentList
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps : RProps {
    var students: Array<Student>
}
fun RBuilder.rstudentList(students: Array<Student>,state : Array<Boolean>, onClick: (Int) -> (Event)->Unit) =
    child(functionalComponent<RStudentListProps> {props ->
        props.students.mapIndexed {index, student ->
            li {
                rstudent(student, state[index],onClick(index))
                }
            }
    }){
        attrs.students = students
    }


