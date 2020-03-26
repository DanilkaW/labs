package component

import data.Lesson
import data.lessonsList
import kotlinx.html.InputType
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface lessonProps :RProps{
    var lessons : Array<Lesson>
}
fun RBuilder.fAddLesson (listLessons :Array<Lesson>, add: Unit) =
    child(functionalComponent<lessonProps> {props ->
            button{
                +"ADD LESSON"
                add
            }
    }){
        attrs.lessons= listLessons
    }