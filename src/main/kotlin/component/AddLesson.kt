package component

import data.Lesson
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface lessonProps :RProps{
    var lessons : Array<Lesson>
}

fun RBuilder.fAddLesson(listLessons: Array<Lesson>, click : (Event) -> Unit) =
    child(functionalComponent<lessonProps> {
        button {
            +"ADD LESSON"
            attrs.onClickFunction = click
        }
    }){
        attrs.lessons=listLessons
    }