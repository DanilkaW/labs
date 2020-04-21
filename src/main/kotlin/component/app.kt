package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.reflect.KClass

interface AppProps : RProps {}

interface AppState : RState {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.lessons = lessonsList
        state.students = studentList
        state.presents = Array(state.lessons.size) {
            Array(state.students.size) { false }
        }

    }

    override fun RBuilder.render() {
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
                    anyList(state.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(state.students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.lessons,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"No such student" }
                }
            )
            route("/editStudents",
            render = {
                anyEdit(
                    RBuilder::anyList,
                    RBuilder::fAddStudent,
                    state.students,
                    addStudent(),
                    deleteStudent(),
                    "Students",
                    "/students"
                )
            })
            route("/editLessons",
            render = {
                anyEdit(
                    RBuilder::anyList,
                    RBuilder::fAddLesson,
                    state.lessons,
                    addLesson(),
                    deleteLesson(),
                    "Lessons",
                    "/lessons"
                )
            })
        }
    }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    fun addLesson():(String) -> Unit = { str->
        setState {
            lessons += Lesson(str)
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun deleteLesson() :(Int) -> Unit = {
        val editedPresents = state.presents.toMutableList().apply { removeAt(it-1) }
            .toTypedArray()
        val editedLessons = state.lessons.toMutableList().apply { removeAt(it-1) }
            .toTypedArray()
        setState{
            lessons = editedLessons
            presents=editedPresents
        }
    }

    fun addStudent():(String) -> Unit = { str->
        val newStr = str.split(" ")
        setState {
            students += Student(newStr[0],newStr[1])
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun deleteStudent() :(Int) -> Unit = {
        val editedStudents = state.students.toMutableList().apply { removeAt(it-1) }
            .toTypedArray()
        val editedPresents = state.presents.toMutableList().apply { removeAt(it-1) }
            .toTypedArray()
        setState{
            students = editedStudents
            presents=editedPresents
        }
    }
}

fun RBuilder.app() =
    child(
        withDisplayName("AppHoc", App::class)
    ) {

    }





