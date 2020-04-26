package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*
import kotlin.browser.document

interface AppProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> { props ->
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li {navLink("/editStudents"){+"Edit Students"} }
                    li {navLink("/editLessons"){+"Edit Lessons"} }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(props.store.getState().lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.store.getState().students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = renderLesson(props)
            )
            route("/students/:number",
                render = renderStudent(props)
            )
            route("/editStudents",
                render = renderEditStudent(props)
            )
            route("/editLessons",
                render = renderEditLesson(props)
                )
        }
    }

fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun AppProps.addStud(): (Event) -> Unit ={
    val nameObj = document.getElementById("Students") as HTMLInputElement
    val newStr = nameObj.value.split(" ")
    store.dispatch(addStudent(newStr[0],newStr[1]))
}

fun AppProps.deleteStudent():(Event) -> Unit = {
    val nameObj = document.getElementById("DeleteStudents") as HTMLInputElement
    store.dispatch(deleteStudent(nameObj.value.toInt()-1))
}

fun AppProps.addLesson(): (Event) -> Unit = {
    val nameObj = document.getElementById("Lessons") as HTMLInputElement
    store.dispatch(addLesson(nameObj.value))
}

fun AppProps.deleteLesson(): (Event) -> Unit = {
    val nameObj = document.getElementById("DeleteLessons") as HTMLInputElement
    store.dispatch(deleteLesson(nameObj.value.toInt()-1))
}

fun RBuilder.renderEditLesson (props: AppProps) ={
    anyEdit(
        RBuilder::anyList,
        RBuilder::fAddLesson,
        props.store.getState().lessons,
        props.addLesson(),
        props.deleteLesson(),
        "Lessons",
        "/lessons"
    )
}

fun RBuilder.renderEditStudent(props: AppProps) = {
    anyEdit(
        RBuilder::anyList,
        RBuilder::fAddStudent,
        props.store.getState().students,
        props.addStud(),
        props.deleteStudent(),
        "Students",
        "/students"
    )
}

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            anyFull(
                RBuilder::student,
                lesson,
                props.store.getState().students,
                props.store.getState().presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            anyFull(
                RBuilder::lesson,
                student,
                props.store.getState().lessons,
                props.store.getState().presents.map {
                    it[num]
                }.toTypedArray(),
                props.onClickStudent(num)
            )
        else
            p { +"No such student" }
    }


fun RBuilder.app(
    store: Store<State, RAction, WrapperAction>
) =
    child(
        withDisplayName("App", fApp())
    ) {
        attrs.store = store
    }





