import csstype.Position
import csstype.px
import emotion.react.css
import kotlinx.browser.document
import react.Fragment
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p

data class Video(
    val id: Int,
    val title: String,
    val speaker: String,
    val videoUrl: String
)


fun main() {
    val container = document.getElementById("root") ?: error("Coudldldl")
    createRoot(container).render(App.create())
}

