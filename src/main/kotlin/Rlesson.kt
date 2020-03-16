import data.Student
import data.studentList
import org.w3c.dom.events.Event
import react.*
import react.dom.h2
import react.dom.ol

interface Rlesson : RProps {
    var lesson : String
    var listStudent :Array<Student>
}

interface RStudentListState :RState{
    var present: Array<Boolean>
}

class RStudentlesson : RComponent<Rlesson,RStudentListState>(){
    override fun componentWillMount() {
        state.apply {
            present = Array(props.listStudent.size){false}
        }
    }

    override fun RBuilder.render() {
        h2 {
            +props.lesson
        }
        ol {
                rstudentList(props.listStudent,state.present, onIndex())
        }
    }
    fun RBuilder.onIndex(): (Int) -> (Event) -> Unit = {
        onClick(it)
    }
    fun RBuilder.onClick(index: Int) :(Event) -> Unit = {
        setState {
            present[index] = !present[index]
        }
    }
}
    fun RBuilder.lesson()=
        child(RStudentlesson::class){
            attrs.lesson = "oop"
            attrs.listStudent = studentList.toTypedArray()
        }
