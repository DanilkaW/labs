package component

import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import kotlin.browser.document

interface lessonProps :RProps{
}

fun RBuilder.fAddLesson( ) =
    child(functionalComponent<lessonProps> {
        input(InputType.text) {
            attrs {
                placeholder = "Write lesson"
                id = "Lessons"
            }
        }
        input(InputType.text){
            attrs{
                placeholder = "Delete (only int)"
                id = "DeleteLessons"
            }
        }
    }){
    }