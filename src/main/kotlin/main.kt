import kotlin.browser.document
import kotlin.js.Date

fun main() {
    val data = Date()
    val today = data.getTime()
    val year = data.getFullYear()
    val day1 = Date(year+1,0,1).getTime()
    document.write("Today is  $data ")
    val mlsec = (day1-today)/(1000 * 60 * 60 * 24)
    document.write("<br> ${mlsec.toInt()} days until The new year")
}
