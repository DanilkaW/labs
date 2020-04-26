package component

import data.Student
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.onChange
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RProps
import react.child
import react.dom.br
import react.dom.button
import react.dom.input
import react.dom.select
import react.functionalComponent
import kotlin.browser.document

interface addStudentProps : RProps {
}
fun RBuilder.fAddStudent () =
    child(functionalComponent<addStudentProps> {
        input(InputType.text) {
            attrs {
                placeholder = "Write full name"
                id = "Students"
            }
        }
        input(InputType.text) {
            attrs {
                placeholder = "Delete (Only int)"
                id = "DeleteStudents"
            }
        }
    }){

    }