import data.Student
import data.studentList
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.js.li
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear

var ascending = true

fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                +"Students"
                onClickFunction = onCLickFunction()
            }
            ol {
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        attributes += "id" to it.firstname
                        +"${it.firstname} ${it.surname}"
                        onClickFunction = presenceFun(it)
                    }
                }
            }
                input(options = arrayListOf("white","blue","red"))
        }
}


private fun LI.presenceFun(Student :Student): (Event) -> Unit {
    return{
        val student = document.getElementById(Student.firstname)!!
        if (Student.presence) {
            student.setAttribute("style", "color:grey")
            Student.presence = false
        }
        else {
            student.setAttribute("style", "color:white")
            Student.presence = true
        }
    }
}

private fun H1.onCLickFunction (): (Event) -> Unit {
    return {
        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)
                studentList.sortBy { it.firstname }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending

            studentList.map {
                li {
                    attributes += "id" to it.firstname
                    +"${it.firstname} ${it.surname}"
                    onClickFunction = presenceFun(it)
                }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.input(options : List<String>,
                                   classes : String? = null,
                                   block : P.() -> Unit = {})
        : HTMLElement = p(classes) {
    options.forEach {
        +it
        input (InputType.radio, name = "colors"){
            value = it
            onClickFunction = {
                val color =  document.getElementById("root")!!
                color.setAttribute("style","color: ${value}")
            }
        }
    }
    block()
}

