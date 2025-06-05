package channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun fetchStockPrice(): Double {
    delay(1000)
    return Math.random() * 100
}

fun main() = runBlocking {
    // Create a Chanel to hold stock prices
    val stockPrices = Channel<Double>()

    // Launch a coroutine to fetch prices every second
    launch {
        while (true) {
            val price = fetchStockPrice()
            stockPrices.send(price)
            delay(1000)
        }
    }

    // Launch a coroutine to consume and display prices
    launch {
        for (price in stockPrices) {
            println("Current Stock Price: $price")
        }
    }

    delay(1000)
}
