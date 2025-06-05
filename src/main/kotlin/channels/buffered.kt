package channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>(capacity = 4)
    val sender = launch {
        repeat(10) {
            println("Sending $it")
            channel.send(it) // will suspend when buffer is full
        }
    }

    delay(1000)

    // no exception
    for (i in channel) println(i)

    // exception - not?
    repeat(15) { println(channel.receive()) }

    sender.cancel()
}
