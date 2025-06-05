package channels

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(300)
        table.send(ball) // send the ball back
    }
}

fun main() = runBlocking {
    val sharedTable = Channel<Ball>()
    launch { player("ping", sharedTable) }
    launch { player("pong", sharedTable) }
    sharedTable.send(Ball(0)) // serve the ball
    delay(1000) // let's play a game for one second
    coroutineContext.cancelChildren() // game over, cancel them
}
