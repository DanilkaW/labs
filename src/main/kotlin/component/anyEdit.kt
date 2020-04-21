package component

import data.studentList
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document

interface anyEditProps<O> :RProps{
    var subObj : Array<O>
    var onAdd : (String) -> Unit
    var onDelete : (Int) -> Unit
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
                attrs.onClickFunction = {
                    val nameObj = document.getElementById(props.name) as HTMLInputElement
                    if(props.name=="Students" && nameObj.value.contains(" "))
                       props.onAdd(nameObj.value)
                    if(props.name=="Lessons")
                        props.onAdd(nameObj.value)
                }
            }
            button {
                +"DELETE"
                attrs.onClickFunction = {
                    val nameObjDel = document.getElementById("Delete"+props.name) as HTMLInputElement
                    if(nameObjDel.value.toInt() > 0 && nameObjDel.value.toInt()<=props.subObj.lastIndex+1)
                    props.onDelete(nameObjDel.value.toInt())
                }
            }
            rComponent(props.subObj,props.name,props.path)
        }
}

fun <O> RBuilder.anyEdit(
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement,
    rAddObj :RBuilder.() -> ReactElement,
    subObj : Array<O>,
    onAdd: (String) -> Unit,
    onDelete: (Int) -> Unit,
    name : String,
    path: String
    ) = child(fanyEdit<O>(rAddObj,rComponent)){
    attrs.onAdd=onAdd
    attrs.subObj=subObj
    attrs.onDelete = onDelete
    attrs.name = name
    attrs.path = path
}