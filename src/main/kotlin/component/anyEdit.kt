package component

import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document

interface anyEditProps<O> :RProps{
    var subObj : Array<O>
    var onAdd : (Event) -> Unit
    var onDelete : (Event) -> Unit
    var name : String
    var path : String
}
fun <O> fanyEdit(
    rAddObj :RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement
) =
    functionalComponent<anyEditProps<O>> {props ->
    h2{+"Edit Page"}
        p {
            +"Edit ${props.name} here:"
            rAddObj()
            br {}
            button {
                +"ADD"
                attrs.onClickFunction = props.onAdd
            }
            button {
                +"DELETE"
                attrs.onClickFunction = props.onDelete
            }
            rComponent(props.subObj,props.name,props.path)
        }
}

fun <O> RBuilder.anyEdit(
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement,
    rAddObj :RBuilder.() -> ReactElement,
    subObj : Array<O>,
    onAdd: (Event) -> Unit,
    onDelete: (Event) -> Unit,
    name : String,
    path: String
    ) = child(fanyEdit<O>(rAddObj,rComponent)){
    attrs.onAdd=onAdd
    attrs.subObj=subObj
    attrs.onDelete = onDelete
    attrs.name = name
    attrs.path = path
}