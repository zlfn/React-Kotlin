import csstype.ClassName
import kotlinx.browser.document
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol


external interface SquareProps : Props {
    var value: String
    var onClick: ()->Unit
}

val Square = FC<SquareProps> {props->
    button {
        className = ClassName("square")
        onClick={value="X"; props.onClick()}
        +props.value
    }
}

external interface BoardProps : Props {
    var squares: Array<String>
    var onClick: (Int)->Unit
}

val Board = FC<BoardProps> { props->
    fun ChildrenBuilder.renderSquare(i: Int) {
        Square {
            value = props.squares[i]
            onClick = {props.onClick(i)}
        }
    }

    div {
        div {
            className = ClassName("board-row")
            renderSquare(0)
            renderSquare(1)
            renderSquare(2)
        }
        div {
            className = ClassName("board-row")
            renderSquare(3)
            renderSquare(4)
            renderSquare(5)
        }
        div {
            className = ClassName("board-row")
            renderSquare(6)
            renderSquare(7)
            renderSquare(8)
        }
    }
}

val Game = FC<Props> {
    var history:List<Array<String>> by useState(arrayListOf(Array(9, {""})))
    var xIsNext by useState(true)
    var stepNumber by useState(0)

    fun handleClick(i: Int) {
        var historyCopy = history.slice(0.. stepNumber)
        val current = historyCopy.last()
        val squares = current.copyOf()
        if (calculateWinner(squares)!="" || squares[i]!="")
            return
        squares[i] = if(xIsNext) "X" else "O"

        stepNumber = historyCopy.size
        historyCopy = historyCopy.plusElement(squares)
        console.log(historyCopy)
        history = historyCopy
        xIsNext = !xIsNext
    }

    fun jumpTo(step: Int) {
        stepNumber = step
        xIsNext = ((step%2) == 0)
        console.log("jump to $step")
    }

    console.log("a$stepNumber")
    val current = history[stepNumber].copyOf()
    console.log("b$stepNumber")
    val winner = calculateWinner(current)
    val moves = List(history.size){ move ->
        val desc =
            if(move!=0) "Go to move #$move"
            else "Go to Game start"
        Fragment.create {
            li {
                key=move.toString()
                button {
                    +desc
                    onClick={jumpTo(move)}
                }
            }
        }
    }
    val status = if(winner!="") {
        "Winner: $winner"
    } else {
        "Next player: ${if(xIsNext) "X" else "O"}"
    }

    div {
        className = ClassName("game")
        div {
            className = ClassName("game-board")
            Board {
                squares=current
                onClick=fun(i:Int){handleClick(i)}
            }
        }
        div {
            className = ClassName("game-info")
            div {
                +status
            }
            ol {
                for(e in moves)
                    +e
            }
        }
    }
}

fun calculateWinner(squares: Array<String>): String {
    val lines = arrayOf(
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),
        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),
        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6),
    )
    for(line in lines) {
        val (a, b, c) = line.slice(0..2)
        if (squares[a]!="" && squares[a]==squares[b] && squares[a]==squares[c]) {
            return squares[a]
        }
    }
    return ""
}

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)
    createRoot(container).render(Game.create())
}