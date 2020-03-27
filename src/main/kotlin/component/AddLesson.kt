package component

import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface lessonProps :RProps{
   var clicks : (String) -> (Event) -> Unit
}
interface lessonState :RState{
   var lesson : String

}
class AddLesson1 : RComponent<lessonProps,lessonState>(){
    override fun RBuilder.render() {
        input(InputType.text){
            attrs{
                onChangeFunction = {
                    val tmp = it.target as HTMLInputElement
                    setState {
                        lesson = tmp.value
                    }
                }
            }
        }
        button {
            +"ADD LESSON"
            attrs.onClickFunction = props.clicks(state.lesson)
        }
    }
}
fun RBuilder.faddLesson (click :(String) -> (Event) -> Unit) =
    child(AddLesson1::class){
    attrs.clicks=click
}
