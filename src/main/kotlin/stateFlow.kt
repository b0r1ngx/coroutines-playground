import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class PlayerState { Playing, Paused, Stopped }

fun main() = runBlocking {
    val playerState = MutableStateFlow(PlayerState.Stopped)
    launch {
        playerState.collect { state ->
            println("Player State: $state")
        }
    }

    launch {
        playerState.emit(PlayerState.Playing).also { delay(2000) }
        playerState.emit(PlayerState.Paused).also { delay(1000) }
        playerState.emit(PlayerState.Stopped)
    }

    delay(5000)
    coroutineContext.cancelChildren()
}
