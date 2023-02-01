import kotlinx.browser.document
import kotlinx.js.timers.Timeout
import kotlinx.js.timers.clearInterval
import kotlinx.js.timers.setInterval
import kotlinx.js.timers.setTimeout
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import kotlin.js.Date

val Clock = FC<Props> {
    var date by useState(Date())

    fun tick() {
        console.log("tick")
        date = Date()
    }

    var ID: Timeout
    useEffect(useRef(0)) {
        console.log("Effect")
        ID = setInterval({tick()},1000)
        cleanup {
            console.log("cleanup")
            clearInterval(ID)
        }
    }

    div {
        h1 {+"Hello, world"}
        h2 {+"It is ${date.toLocaleTimeString()}"}
    }
}


fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)
    val root = createRoot(container)
    root.render(Clock.create())
    setTimeout({root.render(Fragment.create{h1{+"HMM"}})},5000)
    setTimeout({root.render(Clock.create())}, 10000)
}
