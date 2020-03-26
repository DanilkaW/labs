import component.app
import component.fAddLesson
import data.*
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        app(studentList)
    }
}